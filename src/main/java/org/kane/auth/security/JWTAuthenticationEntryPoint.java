package org.kane.auth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kane.domain.DTO.response.InvalidLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
        response.setContentType(SecurityConstants.CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getWriter().println(invalidLoginResponse);
    }


}
