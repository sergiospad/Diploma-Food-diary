package org.kane.web.recipe_recource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;
import org.kane.domain.service.recipe_recource.measure_unit.MeasureUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/measureUnit")
@RequiredArgsConstructor
public class MeasureUnitController {

    private final MeasureUnitService measureUnitService;

    @GetMapping("/all/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<MeasureUnitDTO>> findAllMeasureUnit(@PathVariable Long id){
        var result = measureUnitService.findAllByIngredientID(id);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/measureUnit/create?name=n
     */
    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MeasureUnitDTO> createMeasureUnit(@RequestParam("name") String name){
        var result = measureUnitService.createMeasureUnit(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<MeasureUnitDTO>> findAll(){
        var result = measureUnitService.getAllUnits();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/free/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<MeasureUnitDTO>> findFreeMeasureUnit(@PathVariable Long id){
        var result = measureUnitService.getFreeUnitsByCategoryID(id);
        return ResponseEntity.ok(result);
    }
}
