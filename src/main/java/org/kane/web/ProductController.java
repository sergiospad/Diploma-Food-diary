package org.kane.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.nutritional_info.NutritionShowProjection;
import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;
import org.kane.domain.DTO.entityDTO.product.ProductEditDTO;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Long> createProduct(Principal principal, @RequestBody ProductCreateDTO productCreateDTO){
        var result = productService.createProduct(principal, productCreateDTO);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> updateProduct(@RequestBody ProductEditDTO productEditDTO) {
        productService.updateProduct(productEditDTO);
        return ResponseEntity.ok(new MessageResponse("Product updated successfully"));
    }

    /**
     * GET /api/product/search?searchItem=it
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ProductSearchDTO>> productSearch(@RequestParam("searchItem") String searchItem) {
        var result = productService.searchProduct(searchItem);
        return ResponseEntity.ok(result);
    }

    /**
     *GET /api/product/measureUnits?productId=1
     */
    @GetMapping("/measureUnits")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<MeasureUnitDTO>> getAllMeasureUnitsAll(@RequestParam("productId") Long productId){
        var result = productService.getAllMeasureUnitsDTOByProductId(productId);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/product/nutrition?id=1
     */
    @GetMapping("/nutrition")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<NutritionShowProjection> getNutrition(@RequestParam("id") Long id){
        var result = productService.getNutritionShowProjection(id);
        return ResponseEntity.ok(result);
    }
}
