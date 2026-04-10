package org.kane.domain.DTO.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvalidLoginResponse{
    private final String message = "Invalid Login Request";
    private final String password = "Invalid Password";
}

