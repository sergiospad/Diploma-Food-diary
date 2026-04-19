package org.kane.domain.service.recipe_recource.tag;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> findAllTags();
}
