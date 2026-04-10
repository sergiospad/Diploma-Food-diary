package org.kane.domain.service.tag;

import lombok.RequiredArgsConstructor;
import org.kane.database.repository.tag.TagRepository;
import org.kane.domain.DTO.entityDTO.TagDTO;
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
        return tagRepository.findAll();
    }
}
