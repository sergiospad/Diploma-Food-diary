package org.kane.domain.mappers.product;

import org.kane.database.entity.Product;
import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateMapper implements Mapper<ProductCreateDTO, Product> {
    @Override
    public Product map(ProductCreateDTO from) {
        var product =  Product.builder()
                .description(from.getDescription())
                .build();
        product.setCalories(from.getCalories());
        product.setProtein(from.getProtein());
        product.setFat(from.getFat());
        product.setCarbs(from.getCarbs());
        product.setIsPrivate(from.isPrivate());
        product.setName(from.getTitle());
        return product;
    }
}
