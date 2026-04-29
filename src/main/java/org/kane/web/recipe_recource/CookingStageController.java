package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageShowDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.recipe_recource.cooking_stage.CookingStageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/stage")
@RequiredArgsConstructor
public class CookingStageController {

    private final CookingStageService cookingStageService;

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> editCookingStage(@RequestBody CookingStageEditDescDTO dto) {
        cookingStageService.editCookingStage(dto);
        return ResponseEntity.ok(new MessageResponse("Coefficient Edited Successfully"));
    }

    /**
     * GET /api/stage/all?id=1
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CookingStageShowDTO>> getAllShowDTO(@RequestParam("id") Long id) {
        var result = cookingStageService.getAllShowDTOByRecipeID(id);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/stage/delete?id=1
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> deleteCookingStage(@RequestParam("id") Long id) {
        cookingStageService.removeCookingStage(id);
        return ResponseEntity.ok(new MessageResponse("Cooking Stage Deleted Successfully"));
    }
}
