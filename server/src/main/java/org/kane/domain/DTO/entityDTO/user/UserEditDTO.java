package org.kane.domain.DTO.entityDTO.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.auth.validations.existing_email.ExistingEmail;
import org.kane.auth.validations.existing_username.ExistingUsername;
import org.kane.auth.validations.valid_email.ValidEmail;
import org.kane.database.enum_types.Gender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEditDTO {
    Long id;

    @ExistingUsername
    String username;

    @ValidEmail
    @ExistingEmail
    String email;

    Gender gender;
    LocalDate birthDate;
    Short height;

}
