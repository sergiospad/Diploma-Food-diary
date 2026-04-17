package org.kane.database.repository.product;

import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;
import org.kane.exceptions.not_found.NoSuchProductException;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

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
public class SearchProductRepositoryTest extends IntegrationTestBase {
    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductRepository productRepository;


    @BeforeEach
    void setUp() {
        try {
            Search.session(em)
                    .massIndexer()
                    .startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to initialize Hibernate Search index for tests", e);
        }
    }

    @Test
    void findSearchDTO(){
        var list = productRepository.findSearchDTO("каша");
        assertThat(list).isNotEmpty();
        var expectedList = List.of(6L);
        list.stream()
                .map(ProductSearchDTO::getId)
                .forEach(l-> assertThat(l).isIn(expectedList));
        var expectedNamesList= expectedList.stream()
                .map(productRepository::findById)
                .map(l->l.orElseThrow(()-> new NoSuchProductException("product not found")))
                .map(Product::getName)
                .toList();
        list.stream()
                .map(ProductSearchDTO::getName)
                .forEach(l-> assertThat(l).isIn(expectedNamesList));
    }

}
