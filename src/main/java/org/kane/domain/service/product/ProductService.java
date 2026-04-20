package org.kane.domain.service.product;

import org.kane.domain.DTO.entityDTO.recipe_recource.measure_unit.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;
import org.kane.domain.DTO.entityDTO.product.ProductEditDTO;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;

import java.security.Principal;
import java.util.List;

public interface ProductService {
    Long createProduct(Principal principal, ProductCreateDTO productCreateDTO);
    void updateProduct(ProductEditDTO productEditDTO);
    List<ProductSearchDTO> searchProduct(String searchItem);
    List<MeasureUnitDTO> getAllMeasureUnitsDTOByProductId(Long productId);
    List<Long> addTagToRecipe(Long recipeId, Long tagId);
    List<Long> removeTagFromRecipe(Long recipeId, Long tagId);

    List<ProductSearchDTO> searchForSuitableNutritions(String keyItem);
}
