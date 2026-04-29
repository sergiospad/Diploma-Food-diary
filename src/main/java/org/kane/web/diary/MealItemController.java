package org.kane.web.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.diary.meal_item.MealItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/mealItem")
@RequiredArgsConstructor
public class MealItemController {

    private final MealItemService mealItemService;

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> updateMealItem(@RequestBody MealItemEditDTO mealItemEditDTO){
        mealItemService.updateMealItem(mealItemEditDTO);
        return ResponseEntity.ok(new MessageResponse("Meal Item Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> deleteMealItem(@PathVariable Long id){
        mealItemService.deleteMealItem(id);
        return ResponseEntity.ok(new MessageResponse("Meal Item Deleted Successfully"));
    }
}
