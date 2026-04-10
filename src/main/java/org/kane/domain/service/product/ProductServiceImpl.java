package org.kane.domain.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.repository.category.CategoryRepository;
import org.kane.database.repository.product.ProductRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.product.ProductCreateDTO;
import org.kane.domain.mappers.ProductCreateMapper;
import org.kane.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductCreateMapper productCreateMapper;
    private final CategoryRepository categoryRepository;

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
}
