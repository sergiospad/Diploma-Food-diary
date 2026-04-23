package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryShowDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CategoryAddCoefficientDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.recipe_recource.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all/name")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CategoryNameDTO>> getAll(){
        var result = categoryService.getAll();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryShowDTO> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO){
        var result = categoryService.createCategory(categoryCreateDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all/show")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CategoryShowDTO>> getAllShow(){
        var result = categoryService.getAllShowDTO();
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> editCategory(@RequestBody CategoryNameDTO categoryNameDTO){
        categoryService.editCategory(categoryNameDTO);
        return ResponseEntity.ok(new MessageResponse("Category Edited Successfully"));
    }

    @PatchMapping("/add/coefficient")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryShowDTO> addCoefficient(@RequestBody CategoryAddCoefficientDTO coefficientDTO){
        var result = categoryService.addCoefficient(coefficientDTO);
        return ResponseEntity.ok(result);
    }
}
