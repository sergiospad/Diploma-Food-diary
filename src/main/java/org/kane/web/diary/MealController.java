package org.kane.web.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.diary.meal.MealCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealEditDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealShowDTO;
import org.kane.domain.service.diary.meal.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MealShowDTO>  createMeal(Principal principal, @RequestBody MealCreateDTO mealCreateDTO) {
        var result = mealService.createMeal(principal, mealCreateDTO);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MealShowDTO> editMeal(@RequestBody MealEditDTO mealEditDTO) {
        var result = mealService.editMeal(mealEditDTO);
        return ResponseEntity.ok().body(result);
    }
}
