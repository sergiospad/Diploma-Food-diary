package org.kane.auth.validations.existing_email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingEmailValidator.class)
@Documented
public @interface ExistingEmail {
    String message() default "Email already exists";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
