package org.kane.domain.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.kane.auth.validations.existing_username.ExistingUsername;
import org.kane.auth.validations.existing_email.ExistingEmail;
import org.kane.auth.validations.valid_email.ValidEmail;

@Data
public class SignupRequest {

    @Email(message="It should have email format")
    @NotBlank(message="User email is required")
    @ValidEmail
    @ExistingEmail
    private String email;

    @NotEmpty(message = "Please enter your password")
    private String password;

    @NotEmpty(message = "Please enter your login")
    @ExistingUsername
    private String username;

}
