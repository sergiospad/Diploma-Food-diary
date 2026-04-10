package org.kane.auth.validations.existing_product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.kane.auth.validations.valid_email.ValidEmail;
import org.kane.database.repository.product.ProductRepository;

@RequiredArgsConstructor
public class ExistingProductValidator implements ConstraintValidator<ValidEmail, String> {
    private final ProductRepository productRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !productRepository.existsByName(value);
    }
}
