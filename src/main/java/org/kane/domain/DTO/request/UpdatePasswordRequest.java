package org.kane.domain.DTO.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class UpdatePasswordRequest {
    @NotEmpty
    String oldPassword;

    @NotEmpty
    String newPassword;

}
