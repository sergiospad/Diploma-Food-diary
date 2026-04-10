package org.kane.domain.service.tag;

import org.kane.domain.DTO.entityDTO.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> findAllTags();
}
