package org.kane.util;

import lombok.SneakyThrows;
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

    public enum ImagePrefix{
        USER, COMMENT, RECIPE, STAGES;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private static final String DEFAULT_USER_AVATAR_FILE = "blue-circle-with-white-user_78370-4707.jpg";

    public static final Path DEFAULT_USER_AVATAR_PATH = BUCKET
            .resolve(ImagePrefix.USER.toString())
            .resolve("default")
            .resolve(DEFAULT_USER_AVATAR_FILE);

    @SneakyThrows
    public static Optional<byte[]> get(Path imagePath){

        return Files.exists(imagePath)?
                Optional.of(Files.readAllBytes(imagePath))
                :Optional.empty();
    }

    public static Path getImagePath(String... pathComponents){
        return Path.of( String.join("/", pathComponents));
    }

    @SneakyThrows
    public static void delete(String imagePath) {
        Path path = Path.of(imagePath).normalize();
        Path target = path.startsWith(BUCKET) ? path : BUCKET.resolve(path);
        Files.delete(target);
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
