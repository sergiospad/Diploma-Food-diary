package org.kane.database.repository.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.database.entity.User;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.database.enum_types.NutritionType;
import org.kane.domain.DTO.entityDTO.recipe_recource.measure_unit.MeasureUnitDTO;
import org.kane.exceptions.not_found.NoSuchProductException;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/product/data-product-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class ProductRepositoryTest extends IntegrationTestBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SavedEntities savedEntities;

    private Product savedProduct;
    private Category savedCategory;
    private User savedUser;

    @BeforeEach
    void setUp() {
        savedProduct = savedEntities.getProduct();
        savedCategory = savedEntities.getCategory();
        savedUser = savedEntities.getUser();
        savedProduct.setCategory(savedCategory);
        savedProduct.setAuthor(savedUser);
    }

    @Test
    void findById(){
        var product = productRepository.findById(savedProduct.getId())
                .orElseThrow(()->new NoSuchProductException("Product not found"));

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(savedProduct.getId());
        assertThat(product.getName()).isEqualTo(savedProduct.getName());
        assertThat(product.getCategory().getId()).isEqualTo(savedCategory.getId());
        assertThat(product.getDescription()).isEqualTo(savedProduct.getDescription());
        assertThat(product.getCalories()).isEqualTo(savedProduct.getCalories());
        assertThat(product.getProtein()).isEqualTo(savedProduct.getProtein());
        assertThat(product.getFat()).isEqualTo(savedProduct.getFat());
        assertThat(product.getCarbs()).isEqualTo(savedProduct.getCarbs());
        assertThat(product.getIsPrivate()).isEqualTo(savedProduct.getIsPrivate());
        assertThat(product.getAuthor().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    void existsByName() {
        assertTrue(productRepository.existsByName(savedProduct.getName()));
        assertFalse(productRepository.existsByName("asdsd"));
    }

    @Test
    void findMeasureUnitDTOByProductId(){
        var list = productRepository.findMeasureUnitDTOByProductId(savedProduct.getId());
        assertThat(list).isNotEmpty();
        var expectedList = List.of(1L, 2L);
        list.stream()
                .map(MeasureUnitDTO::getId)
                .forEach(l->assertThat(l).isIn(expectedList));
    }

    @Test
    void findNameById(){
        var name = productRepository.findNameById(savedProduct.getId());
        assertThat(name).isNotNull()
                .isEqualTo(savedProduct.getName());
    }

    @Test
    void getNutritionsShowProjection(){
        var nutritionShowProjection = productRepository.getNutritionsShowProjection(savedProduct.getId());
        assertThat(nutritionShowProjection).isNotNull();
        assertThat(nutritionShowProjection.getID()).isEqualTo(savedProduct.getId());
        assertThat(nutritionShowProjection.getName()).isEqualTo(savedProduct.getName());
        assertThat(nutritionShowProjection.getType()).isEqualTo(NutritionType.PRODUCT);
        assertThat(nutritionShowProjection.getCalories()).isEqualTo(savedProduct.getCalories());
        assertThat(nutritionShowProjection.getProtein()).isEqualTo(savedProduct.getProtein());
        assertThat(nutritionShowProjection.getFat()).isEqualTo(savedProduct.getFat());
        assertThat(nutritionShowProjection.getCarbs()).isEqualTo(savedProduct.getCarbs());
    }



}