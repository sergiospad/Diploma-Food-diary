package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientChangeDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientShowDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.recipe_recource.ingredient.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> removeIngredient(@PathVariable Long id){
        ingredientService.removeIngredient(id);
        return ResponseEntity.ok(new MessageResponse("Ingredient has been removed"));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<IngredientShowDTO>> getShowIngredients(@PathVariable Long id){
        var result = ingredientService.getShowIngredients(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/toggle")
    @PreAuthorize("permitAll()")
    public ResponseEntity<IngredientShowDTO> toggleMeasureUnit(@RequestBody IngredientChangeDTO ingredientChangeDTO){
        var result = ingredientService.toggleMeasureUnit(ingredientChangeDTO);
        return ResponseEntity.ok(result);
    }
}
