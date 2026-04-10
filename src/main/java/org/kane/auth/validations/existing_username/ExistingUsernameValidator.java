package org.kane.auth.validations.existing_username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.kane.auth.validations.valid_email.ValidEmail;
import org.kane.database.repository.user.UserRepository;

@RequiredArgsConstructor
public class ExistingUsernameValidator implements ConstraintValidator<ExistingUsername, String> {
    private final UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(value);
    }
}
