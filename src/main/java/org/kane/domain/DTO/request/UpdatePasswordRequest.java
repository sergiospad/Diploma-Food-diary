package org.kane.domain.DTO.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.kane.auth.validations.password_match.PasswordMatches;

@Data
@PasswordMatches
public class UpdatePasswordRequest {
    @NotEmpty
    String oldPassword;

    @NotEmpty
    String newPassword;

    @NotEmpty
    String confirmPassword;
}
