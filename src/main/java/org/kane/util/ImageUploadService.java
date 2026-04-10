package org.kane.util;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@UtilityClass
public class ImageUploadService {

    private static final Path BUCKET = Path.of("images");

    @Value("${app.image.user.default}")
    private String DEFAULT_AVATAR;

    public static final Path DEFAULT_USER_AVATAR_PATH;
    static {
        DEFAULT_USER_AVATAR_PATH = BUCKET
                .resolve(ImagePrefix.USER.toString())
                .resolve(DEFAULT_AVATAR);
    }

    public enum ImagePrefix{
        USER, COMMENT, RECIPE, STAGES;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    @SneakyThrows
    public static Optional<byte[]> get(Path imagePath){
        Path fullPath = BUCKET.resolve(imagePath.toString());
        return Files.exists(fullPath)?
                Optional.of(Files.readAllBytes(fullPath))
                :Optional.empty();
    }

    public static Path getImagePath(String... pathComponents){
        return Path.of( String.join("/", pathComponents));
    }

    @SneakyThrows
    public static void delete(String imagePath) {
        Files.delete(BUCKET.resolve(imagePath));
    }

    @SneakyThrows
    public static Path saveImage(MultipartFile file, long id, String... prefixes) {
        var prefix = String.join("/", prefixes);
        var imagePath = getImagePath(BUCKET.toString(), prefix, Long.toString(id), file.getOriginalFilename());
        try(var inputStream = file.getInputStream()) {
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath,
                    inputStream.readAllBytes(),
                    CREATE, TRUNCATE_EXISTING);
        }
        return imagePath;
    }
}
