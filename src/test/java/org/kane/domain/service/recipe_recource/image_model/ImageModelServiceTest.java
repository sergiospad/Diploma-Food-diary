package org.kane.domain.service.recipe_recource.image_model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.kane.database.enum_types.ImageType;
import org.kane.database.repository.recipe_recource.image_model.ImageModelRepository;
import org.kane.exceptions.not_found.ImageNotFoundException;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/user/data-user-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-image-model-service-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class ImageModelServiceTest extends IntegrationTestServiceBase {

    private static final Principal JOHN = () -> "john_doe";

    @Autowired
    private ImageModelService imageModelService;
    @Autowired
    private ImageModelRepository imageModelRepository;

    @AfterEach
    void removeTestFiles() throws IOException {
        deleteSubtree(Path.of("images", "integration"));
        deleteSubtree(Path.of("images", "recipe", "1"));
    }

    private static void deleteSubtree(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (var walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException ignored) {
                }
            });
        }
    }

    @Test
    void getImage_readsFileFromBucketRelativeUrl() throws Exception {
        Path onDisk = Path.of("images", "integration", "901.bin");
        Files.createDirectories(onDisk.getParent());
        Files.write(onDisk, new byte[]{11, 22, 33});

        byte[] bytes = imageModelService.getImage(901L);

        assertThat(bytes).containsExactly(11, 22, 33);
    }

    @Test
    void getImage_throwsWhenRowMissing() {
        assertThatThrownBy(() -> imageModelService.getImage(99999L))
                .isInstanceOf(ImageNotFoundException.class)
                .hasMessageContaining("Image not found");
    }

    @Test
    void uploadImage_persistsRowAndWritesFile() throws Exception {
        var file = new MockMultipartFile("file", "dish.png", "image/png", new byte[]{1, 2, 3, 4});

        Long id = imageModelService.uploadImage(JOHN, file, ImageType.RECIPE);

        assertThat(id).isNotNull();
        var saved = imageModelRepository.findById(id).orElseThrow();
        assertThat(saved.getImageType()).isEqualTo(ImageType.RECIPE);
        assertThat(saved.getUrl()).isNotNull();
        Path written = Path.of(saved.getUrl().toString());
        assertThat(Files.exists(written)).isTrue();
        assertThat(Files.readAllBytes(written)).containsExactly(1, 2, 3, 4);
    }

    @Test
    void updateImage_rewritesFileAndUpdatesRow() throws Exception {
        Path onDisk = Path.of("images", "integration", "901.bin");
        Files.createDirectories(onDisk.getParent());
        Files.writeString(onDisk, "old");
        var newFile = new MockMultipartFile("file", "after.png", "image/png", new byte[]{8, 9});

        imageModelService.updateImage(JOHN, newFile, 901L);

        var updated = imageModelRepository.findById(901L).orElseThrow();
        assertThat(Files.exists(onDisk)).isFalse();
        Path newLocation = Path.of(updated.getUrl().toString());
        assertThat(Files.exists(newLocation)).isTrue();
        assertThat(Files.readAllBytes(newLocation)).containsExactly(8, 9);
    }

    @Test
    void deleteImage_removesRow() {
        assertThat(imageModelRepository.findById(901L)).isPresent();

        imageModelService.deleteImage(901L);

        assertThat(imageModelRepository.findById(901L)).isEmpty();
    }
}
