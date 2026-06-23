package org.kane.domain.service.product;

import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.NutritionType;
import org.kane.database.repository.product.ProductRepository;
import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;
import org.kane.domain.DTO.entityDTO.product.ProductEditDTO;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;


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
class ProductServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SavedEntities savedEntities;

    @Test
    @Transactional
    void createProduct() {
        Principal principal = () -> "lisa_cook";
        var pcdto = ProductCreateDTO.builder()
                .title("testTitle")
                .description("testDescription")
                .categoryId(5L)
                .calories(new Calories(100.0))
                .protein(new Protein(10.0))
                .fat(new Fat(5.0))
                .carbs(new Carbs(15.0))
                .isPrivate(true)
                .build();
        Long productID = productService.createProduct(principal, pcdto);
        assertThat(productID).isNotNull().isEqualTo(11L);
        var product = productRepository.findById(productID).orElseThrow();
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(productID);
        assertThat(product.getDescription()).isEqualTo(pcdto.getDescription());
        assertThat(product.getCategory().getId()).isEqualTo(pcdto.getCategoryId());
        assertThat(product.getCalories()).isEqualTo(pcdto.getCalories());
        assertThat(product.getProtein()).isEqualTo(pcdto.getProtein());
        assertThat(product.getFat()).isEqualTo(pcdto.getFat());
        assertThat(product.getCarbs()).isEqualTo(pcdto.getCarbs());
        assertThat(product.getIsPrivate()).isEqualTo(pcdto.isPrivate());
        assertThat(product.getAuthor().getUsername()).isEqualTo(principal.getName());
    }

    @Test
    void updateProduct() {
        var pedto = ProductEditDTO.builder()
                .id(10L)
                .title("testTitle")
                .description("testDescription")
                .categoryId(5L)
                .calories(new Calories(100.0))
                .protein(new Protein(10.0))
                .fat(new Fat(5.0))
                .carbs(new Carbs(15.0))
                .isPrivate(true)
                .build();
        productService.updateProduct(pedto);
        var product = productRepository.findById(pedto.getId()).orElseThrow();
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(pedto.getId());
        assertThat(product.getName()).isEqualTo(pedto.getTitle());
        assertThat(product.getDescription()).isEqualTo(pedto.getDescription());
        assertThat(product.getCategory().getId()).isEqualTo(pedto.getCategoryId());
        assertThat(product.getCalories()).isEqualTo(pedto.getCalories());
        assertThat(product.getProtein()).isEqualTo(pedto.getProtein());
        assertThat(product.getFat()).isEqualTo(pedto.getFat());
        assertThat(product.getCarbs()).isEqualTo(pedto.getCarbs());
        assertThat(product.getCategory().getId()).isEqualTo(pedto.getCategoryId());
        assertThat(product.getIsPrivate()).isEqualTo(pedto.getIsPrivate());
    }

    @Transactional
    @Test
    void updateProduct2() {
        var pedto = ProductEditDTO.builder()
                .id(10L)
                .title("testTitle")
                .description("testDescription")
                .categoryId(5L)
                .build();
        var oldProduct = productRepository.findById(pedto.getId()).orElseThrow();
        productService.updateProduct(pedto);
        var newProduct = productRepository.findById(pedto.getId()).orElseThrow();
        assertThat(newProduct.getId()).isEqualTo(oldProduct.getId());
        assertThat(newProduct.getDescription()).isEqualTo(pedto.getDescription());
        assertThat(newProduct.getCategory().getId()).isEqualTo(pedto.getCategoryId());
        assertThat(newProduct.getName()).isEqualTo(pedto.getTitle());
        assertThat(newProduct.getCalories()).isEqualTo(oldProduct.getCalories());
        assertThat(newProduct.getProtein()).isEqualTo(oldProduct.getProtein());
        assertThat(newProduct.getFat()).isEqualTo(oldProduct.getFat());
        assertThat(newProduct.getCarbs()).isEqualTo(oldProduct.getCarbs());
        assertThat(newProduct.getIsPrivate()).isEqualTo(oldProduct.getIsPrivate());
    }

    @Test
    @Transactional
    void searchProduct() throws InterruptedException {
        Search.session(entityManager).massIndexer(Product.class).startAndWait();
        var list = productService.searchProduct("Куриная грудка");
        assertThat(list).isNotNull().hasSize(1);
        assertThat(list.stream().map(ProductSearchDTO::getId).toList()).containsExactlyInAnyOrder(1L);

    }

    @Test
    void getAllMeasureUnitsDTOByProductId() {
        var mesList = productService.getAllMeasureUnitsDTOByProductId(1L);
        assertThat(mesList).isNotNull().hasSize(2);
        assertThat(mesList.stream().map(MeasureUnitDTO::getId).toList()).containsExactlyInAnyOrder(1L, 2L);
        assertThat(mesList.stream().map(MeasureUnitDTO::getName).toList()).containsExactlyInAnyOrder("г", "кг");
    }


    @Test
    void getNutritionShowProjection() {
        var savedProduct = savedEntities.getProduct();
        var proj = productService.getNutritionShowProjection(savedProduct.getId());
        assertThat(proj).isNotNull();
        assertThat(proj.getID()).isEqualTo(savedProduct.getId());
        assertThat(proj.getName()).isEqualTo(savedProduct.getName());
        assertThat(proj.getCalories()).isEqualTo(savedProduct.getCalories());
        assertThat(proj.getProtein()).isEqualTo(savedProduct.getProtein());
        assertThat(proj.getFat()).isEqualTo(savedProduct.getFat());
        assertThat(proj.getCarbs()).isEqualTo(savedProduct.getCarbs());
        assertThat(proj.getType()).isEqualTo(NutritionType.PRODUCT);
    }
}