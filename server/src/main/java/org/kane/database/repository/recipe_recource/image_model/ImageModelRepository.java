package org.kane.database.repository.recipe_recource.image_model;

import org.kane.database.entity.recipe_recource.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageModelRepository extends JpaRepository<ImageModel, Long> {
    void deleteImageModelById(Long id);
}
