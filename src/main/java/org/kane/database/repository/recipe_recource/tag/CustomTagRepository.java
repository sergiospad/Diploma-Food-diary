package org.kane.database.repository.recipe_recource.tag;

import org.kane.database.entity.recipe_recource.Tag;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.TagDTO;

import java.util.List;

public interface CustomTagRepository {
    public List<TagDTO> findAllTags();

    List<TagDTO> findAllTagsOfRecipe(Long recipeID);

    List<Tag> findAllTagsByListId(List<Long> listId);
}
