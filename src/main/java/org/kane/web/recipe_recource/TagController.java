package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe_recource.TagDTO;
import org.kane.domain.service.recipe_recource.tag.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        var result = tagService.findAllTags();
        return ResponseEntity.ok(result);
    }
}
