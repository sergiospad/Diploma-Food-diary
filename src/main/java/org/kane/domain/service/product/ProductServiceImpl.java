package org.kane.domain.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.repository.recipe_recource.category.CategoryRepository;
import org.kane.database.repository.product.ProductRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.measure_unit.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.nutritional_info.NutritionShowProjection;
import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;
import org.kane.domain.DTO.entityDTO.product.ProductEditDTO;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;
import org.kane.domain.mappers.product.ProductCreateMapper;
import org.kane.domain.mappers.product.ProductEditMapper;
import org.kane.exceptions.not_found.CategoryNotFoundException;
import org.kane.exceptions.not_found.NoSuchProductException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductCreateMapper productCreateMapper;
    private final CategoryRepository categoryRepository;
    private final ProductEditMapper productEditMapper;

    @Override
    public Long createProduct(Principal principal, ProductCreateDTO productCreateDTO) {
        var user = userRepository.getCurrentUser(principal);
        var product = productCreateMapper.map(productCreateDTO);
        product.setAuthor(user);
        var category = categoryRepository.findById(productCreateDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        product.setCategory(category);
        product = productRepository.save(product);
        return product.getId();
    }

    @Override
    public void updateProduct(ProductEditDTO productEditDTO) {
        var product = productRepository.findById(productEditDTO.getId())
                .map(pr -> productEditMapper.copyMap(productEditDTO, pr))
                .orElseThrow(() -> new NoSuchProductException("Product not found"));

        var category = categoryRepository.findById(productEditDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public List<ProductSearchDTO> searchProduct(String searchItem) {
        return productRepository.findSearchDTO(searchItem);
    }

    @Override
    public List<MeasureUnitDTO> getAllMeasureUnitsDTOByProductId(Long productId) {
        return productRepository.findMeasureUnitDTOByProductId(productId);
    }

    @Override
    public List<Long> addTagToRecipe(Long recipeId, Long tagId) {
        return List.of();
    }

    @Override
    public List<Long> removeTagFromRecipe(Long recipeId, Long tagId) {
        return List.of();
    }

    @Override
    public List<ProductSearchDTO> searchForSuitableNutritions(String keyItem){
        return productRepository.findSearchDTO(keyItem);
    }

    public NutritionShowProjection getNutritionShowProjection(Long id){
        return productRepository.getNutritionsShowProjection(id);
    }
}
