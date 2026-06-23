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
        to.setDescription(Optional.ofNullable(from.getDescription()).orElse(to.getDescription()));
        to.setIsPrivate(Optional.ofNullable(from.getIsPrivate()).orElse(to.getIsPrivate()));
        to.setCalories(Optional.ofNullable(from.getCalories()).orElse(to.getCalories()));
        to.setProtein(Optional.ofNullable(from.getProtein()).orElse(to.getProtein()));
        to.setFat(Optional.ofNullable(from.getFat()).orElse(to.getFat()));
        to.setCarbs(Optional.ofNullable(from.getCarbs()).orElse(to.getCarbs()));
        return to;
    }
}
