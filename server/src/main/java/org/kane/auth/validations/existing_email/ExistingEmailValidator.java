package org.kane.auth.validations.existing_email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.kane.database.repository.user.UserRepository;

@RequiredArgsConstructor
public class ExistingEmailValidator implements ConstraintValidator<ExistingEmail, String> {
    private final UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }

}
