package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientEditDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;
import org.kane.domain.service.recipe_recource.coefficient.CoefficientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/coefficient")
@RequiredArgsConstructor
public class CoefficientController {

    private final CoefficientService coefficientService;

    @PatchMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CoefficientShowDTO> editCoefficient(@RequestBody CoefficientEditDTO coefficientEditDTO) {
        var result = coefficientService.editCoefficient(coefficientEditDTO);
        return ResponseEntity.ok(result);
    }

}
