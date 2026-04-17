package org.kane.database.repository.recipe_recource.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.recipe_recource.Tag;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-tag-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CustomTagRepositoryImplTest extends IntegrationTestBase {


    private List<Tag> savedTags;
    private Recipe savedRecipe;
    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        savedRecipe = savedEntities.getRecipe();
        savedTags = List.of(
                Tag.builder().id(1L).name("Супы").build(),
                Tag.builder().id(5L).name("Диетическое").build());
        savedRecipe.setTags(savedTags);

    }
    @Test
    void findAllTags() {
        var tags = tagRepository.findAllTags();
        assertThat(tags).isNotEmpty().hasSize(8);
        System.out.println(tags);
    }

    @Test
    void findAllTagsOfRecipe() {
        var tags = tagRepository.findAllTagsOfRecipe(savedRecipe.getId());
        assertThat(tags).isNotEmpty().hasSize(savedTags.size());
        tags.forEach(tag -> {
            var tagsName = savedTags.stream().map(Tag::getName).toList();
            var tagsId = savedTags.stream().map(Tag::getId).toList();
            assertThat(tag.getId()).isIn(tagsId);
            assertThat(tag.getName()).isIn(tagsName);
        });
    }
}