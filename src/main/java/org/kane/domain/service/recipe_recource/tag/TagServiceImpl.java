package org.kane.domain.service.recipe_recource.tag;

import lombok.RequiredArgsConstructor;
import org.kane.database.repository.recipe_recource.tag.TagRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.TagDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagDTO> findAllTags(){
        return tagRepository.findAllTags();
    }
}
