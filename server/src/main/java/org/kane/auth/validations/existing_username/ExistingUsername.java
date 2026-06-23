package org.kane.auth.validations.existing_username;

import jakarta.validation.Payload;

public @interface ExistingUsername {
    String message() default "Username already exists";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
