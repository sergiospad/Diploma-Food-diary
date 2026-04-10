package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.PathConverter;
import org.kane.database.enum_types.ImageType;

import java.nio.file.Path;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    @Convert(converter = PathConverter.class)
    private Path url;

    @Column
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
}
