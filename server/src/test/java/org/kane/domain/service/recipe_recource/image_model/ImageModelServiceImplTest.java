package org.kane.domain.service.recipe_recource.image_model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.enum_types.ImageType;
import org.kane.database.repository.recipe_recource.image_model.ImageModelRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.exceptions.not_found.ImageNotFoundException;
import org.kane.util.ImageUploadService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageModelServiceImplTest {

    private static final Principal PRINCIPAL = () -> "john_doe";

    @Mock
    UserRepository userRepository;
    @Mock
    ImageModelRepository imageModelRepository;

    @InjectMocks
    ImageModelServiceImpl underTest;

    @Test
    void uploadImage_savesModelAndReturnsId() {
        when(userRepository.getCurrentUserId(PRINCIPAL)).thenReturn(5L);
        var file = new MockMultipartFile("file", "shot.png", "image/png", new byte[]{9, 8, 7});

        try (MockedStatic<ImageUploadService> img = org.mockito.Mockito.mockStatic(ImageUploadService.class)) {
            img.when(() -> ImageUploadService.saveImage(eq(file), eq(5L), eq("recipe")))
                    .thenReturn(Path.of("images", "recipe", "5", "shot.png"));
            when(imageModelRepository.save(any(ImageModel.class)))
                    .thenAnswer(invocation -> {
                        ImageModel m = invocation.getArgument(0);
                        m.setId(99L);
                        return m;
                    });

            Long id = underTest.uploadImage(PRINCIPAL, file, ImageType.RECIPE);

            assertThat(id).isEqualTo(99L);
            verify(imageModelRepository).save(argThat(m ->
                    m.getImageType() == ImageType.RECIPE
                            && m.getUrl().equals(Path.of("images", "recipe", "5", "shot.png"))));
        }
    }

    @Test
    void getImage_returnsBytesWhenFileExists() {
        Path stored = Path.of("integration", "read.bin");
        var entity = ImageModel.builder()
                .id(1L)
                .url(stored)
                .imageType(ImageType.RECIPE)
                .build();
        when(imageModelRepository.findById(1L)).thenReturn(Optional.of(entity));

        try (MockedStatic<ImageUploadService> img = org.mockito.Mockito.mockStatic(ImageUploadService.class)) {
            img.when(() -> ImageUploadService.get(stored)).thenReturn(Optional.of(new byte[]{1, 2, 3}));

            assertThat(underTest.getImage(1L)).containsExactly(1, 2, 3);
        }
    }

    @Test
    void getImage_throwsWhenMissing() {
        when(imageModelRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getImage(404L))
                .isInstanceOf(ImageNotFoundException.class)
                .hasMessageContaining("Image not found");
    }

    @Test
    void getImage_throwsWhenFileMissingOnDisk() {
        Path stored = Path.of("missing", "x.bin");
        when(imageModelRepository.findById(2L))
                .thenReturn(Optional.of(ImageModel.builder().id(2L).url(stored).imageType(ImageType.USER).build()));

        try (MockedStatic<ImageUploadService> img = org.mockito.Mockito.mockStatic(ImageUploadService.class)) {
            img.when(() -> ImageUploadService.get(stored)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> underTest.getImage(2L))
                    .isInstanceOf(ImageNotFoundException.class);
        }
    }

    @Test
    void updateImage_replacesFileAndSaves() {
        when(userRepository.getCurrentUserId(PRINCIPAL)).thenReturn(7L);
        Path oldUrl = Path.of("recipe", "7", "old.png");
        var existing = ImageModel.builder()
                .id(10L)
                .url(oldUrl)
                .imageType(ImageType.RECIPE)
                .build();
        when(imageModelRepository.findById(10L)).thenReturn(Optional.of(existing));
        var file = new MockMultipartFile("file", "new.png", "image/png", new byte[]{4, 5});

        try (MockedStatic<ImageUploadService> img = org.mockito.Mockito.mockStatic(ImageUploadService.class)) {
            img.when(() -> ImageUploadService.delete(anyString())).thenAnswer(inv -> null);
            img.when(() -> ImageUploadService.saveImage(eq(file), eq(7L), eq("recipe")))
                    .thenReturn(Path.of("images", "recipe", "7", "new.png"));

            underTest.updateImage(PRINCIPAL, file, 10L);

            verify(imageModelRepository).save(argThat(m ->
                    m.getUrl().equals(Path.of("images", "recipe", "7", "new.png"))
                            && m.getImageType() == ImageType.RECIPE));
        }
    }

    @Test
    void updateImage_throwsWhenEntityMissing() {
        when(userRepository.getCurrentUserId(PRINCIPAL)).thenReturn(1L);
        when(imageModelRepository.findById(99L)).thenReturn(Optional.empty());
        var file = new MockMultipartFile("file", "x.png", "image/png", new byte[]{});

        assertThatThrownBy(() -> underTest.updateImage(PRINCIPAL, file, 99L))
                .isInstanceOf(ImageNotFoundException.class);
    }

    @Test
    void deleteImage_delegatesToRepository() {
        underTest.deleteImage(55L);
        verify(imageModelRepository).deleteById(55L);
    }
}
