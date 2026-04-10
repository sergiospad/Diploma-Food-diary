package org.kane.auth.validations.existing_product;

import jakarta.validation.Constraint;
import org.kane.auth.validations.valid_email.EmailValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingProductValidator.class)
@Documented
public @interface ExistingProduct {
}
