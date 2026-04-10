package org.kane.domain.service.recipe;

import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface RecipeService {
    List<RecipePreviewDTO> findPreviews(Principal principal, RecipePreviewRequest request, Pageable pageable);
}
