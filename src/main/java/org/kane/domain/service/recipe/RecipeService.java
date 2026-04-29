package org.kane.domain.service.recipe;

import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.*;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface RecipeService {
    List<RecipePreviewDTO> findPreviews(Principal principal, RecipePreviewRequest request, Pageable pageable);
    List<RecipeSummarySearchDTO> searchBySummary(String searchItem);
    List<RecipeTitleSearchDTO> searchByTitle(String searchItem);
    RecipeShowDTO createRecipe(Principal principal, RecipeCreateDTO recipeCreateDTO);
    RecipeShowDTO updateRecipe(RecipeEditDTO recipeEditDTO);

    RecipeShowDTO showRecipe(Long recipeID);

    Long getAuthorOfRecipe(Long recipeID);
}
