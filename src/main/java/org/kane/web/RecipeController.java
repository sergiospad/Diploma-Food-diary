package org.kane.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe.*;
import org.kane.domain.DTO.entityDTO.recipe_recource.FavouriteRecipeDTO;
import org.kane.domain.DTO.request.FavouritesRequest;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.recipe.RecipeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
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

    /**
     * POST /api/recipe/previews?page=1
     */
    @PostMapping("/previews")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<RecipePreviewDTO>> getAllRecipePreviews(
            Principal principal,
            @RequestBody RecipePreviewRequest recipePreviewRequest,
            @PageableDefault(size = 12) Pageable pageable) {
        return ResponseEntity.ok(recipeService.findPreviews(principal, recipePreviewRequest, pageable));
    }

    /**
     * GET /api/recipe/search/summary?searchItem=it
     */
    @GetMapping("/search/summary")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<RecipeSummarySearchDTO>> summarySearch(@RequestParam("searchItem") String searchItem) {
        var result = recipeService.searchBySummary(searchItem);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/recipe/search/title?searchItem=it
     */
    @GetMapping("/search/title")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<RecipeTitleSearchDTO>> titleSearch(@RequestParam("titleSearch") String titleSearch) {
        var result = recipeService.searchByTitle(titleSearch);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RecipeShowDTO> createRecipe(Principal principal, @RequestBody RecipeCreateDTO recipeCreateDTO) {
        var result = recipeService.createRecipe(principal, recipeCreateDTO);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RecipeShowDTO> updateRecipe(@RequestBody RecipeEditDTO recipeEditDTO) {
        var result = recipeService.updateRecipe(recipeEditDTO);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/show/{recipeID}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<RecipeShowDTO> showRecipe(@PathVariable Long recipeID) {
        var result = recipeService.showRecipe(recipeID);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/author/{recipeID}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Long> findAuthorByRecipe(@PathVariable Long recipeID) {
        return ResponseEntity.ok(recipeService.getAuthorOfRecipe(recipeID));
    }

    /**
     * POST /api/recipe/favourites — тело JSON (список id рецептов).
     * GET с {@link RequestBody} не используем: у многих клиентов тело GET отбрасывается, preflight/CORS и кэши иначе ведут себя.
     */
    @PostMapping(value = "/favourites")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<FavouriteRecipeDTO> getFavourites(
            Principal principal,
            @RequestBody FavouritesRequest request) {
        return ResponseEntity.ok(recipeService.getFavourites(principal, request));
    }

    @GetMapping("/toggle/{recipeID}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse>  toggleFavourite(Principal principal, @PathVariable Long recipeID) {
        recipeService.toggleFavourite(principal, recipeID);
        return ResponseEntity.ok(new MessageResponse("Обновлено"));
    }
}
