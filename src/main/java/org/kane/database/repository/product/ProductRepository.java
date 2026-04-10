package org.kane.database.repository.product;

import org.kane.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {

    boolean existsByName(String name);
}
