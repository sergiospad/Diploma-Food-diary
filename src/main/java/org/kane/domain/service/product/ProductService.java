package org.kane.domain.service.product;

import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;

import java.security.Principal;

public interface ProductService {
    public Long createProduct(Principal principal, ProductCreateDTO productCreateDTO);

}
