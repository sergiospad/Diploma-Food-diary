package org.kane.domain.mappers.product;

import org.kane.database.entity.Product;
import org.kane.domain.DTO.entityDTO.product.ProductEditDTO;
import org.kane.domain.mappers.CopyMapper;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductEditMapper implements CopyMapper<ProductEditDTO, Product> {
    @Override
    public Product copyMap(ProductEditDTO from, Product to) {
        to.setName(from.getTitle());
        to.setDescription(from.getDescription());
        to.setIsPrivate(from.getIsPrivate());
        to.setCalories(from.getCalories());
        to.setProtein(from.getProtein());
        to.setFat(from.getFat());
        to.setCarbs(from.getCarbs());
        return to;
    }
}
