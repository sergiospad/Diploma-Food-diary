package org.kane.database.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.file.Path;

@Converter(autoApply = true)
public class PathConverter implements AttributeConverter<Path, String> {
    @Override
    public String convertToDatabaseColumn(Path path) {
        return path.toString();
    }

    @Override
    public Path convertToEntityAttribute(String s) {
        return Path.of(s);
    }
}
