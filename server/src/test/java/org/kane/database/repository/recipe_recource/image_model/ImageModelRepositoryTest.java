package org.kane.database.repository.recipe_recource.image_model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.repository.recipe_recource.cooking_stage.CookingStageRepository;
import org.kane.exceptions.not_found.ImageNotFoundException;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-image-model-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class ImageModelRepositoryTest extends IntegrationTestBase {
        @Autowired
        private ImageModelRepository imageModelRepository;

        @Autowired
        private SavedEntities savedEntities;

        private ImageModel savedImageModel;

        @BeforeEach
        void setUp()  {
                savedImageModel = savedEntities.getAvatar();
        }

        @Test
        void findById() {
                var img =  imageModelRepository.findById(1L)
                        .orElseThrow(()->new ImageNotFoundException("Image not found"));
                assertThat(img).isNotNull();
                assertThat(img.getId()).isEqualTo(savedImageModel.getId());
                assertThat(img.getImageType()).isEqualTo(savedImageModel.getImageType());
                assertThat(img.getUrl()).isEqualTo(savedImageModel.getUrl());
        }

        @Test
        void deleteImageModelById() {
                var img =  imageModelRepository.findAll();
                assertThat(img).isNotNull().hasSize(8);
                imageModelRepository.deleteById(savedImageModel.getId());
                assertThat(imageModelRepository.findAll()).hasSize(7);
        }
}