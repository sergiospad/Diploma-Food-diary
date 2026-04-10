package org.kane.auth.security;

import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.request.LoginRequest;
import org.kane.domain.DTO.response.JWTTokenResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenProvider jwtTokenProvider;

    public JWTTokenResponse generateToken(LoginRequest loginRequest) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenProvider.generateToken(Map.of("role", userDetails.getRole()), userDetails.getUsername());
        return JWTTokenResponse.builder()
                .token(token)
                .success(true)
                .build();
    }
}
