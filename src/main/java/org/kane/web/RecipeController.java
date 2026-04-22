package org.kane.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeSummarySearchDTO;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.kane.domain.service.recipe.RecipeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/previews")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<RecipePreviewDTO>> getAllRecipePreviews(
            Principal principal,
            @RequestBody RecipePreviewRequest recipePreviewRequest,
            @PageableDefault(size = 18) Pageable pageable) {
        return ResponseEntity.ok(recipeService.findPreviews(principal, recipePreviewRequest, pageable));
    }

    @GetMapping("/summary/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<RecipeSummarySearchDTO>> summarySearch(@ModelAttribute String searchItem) {
        var result = recipeService.searchBySummary(searchItem);
        return ResponseEntity.ok(result);
    }
}
