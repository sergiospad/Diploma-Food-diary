package org.kane.database.repository.tag;

import org.kane.domain.DTO.entityDTO.TagDTO;

import java.util.List;

public interface CustomTagRepository {
    public List<TagDTO> findAllByPriorityDesc();
}
