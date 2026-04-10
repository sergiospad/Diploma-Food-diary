package org.kane.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.auth.security.CustomUserDetailsService;
import org.kane.auth.security.TokenService;
import org.kane.auth.validations.ResponseErrorValidation;
import org.kane.domain.DTO.request.LoginRequest;
import org.kane.domain.DTO.request.SignupRequest;
import org.kane.domain.DTO.response.JWTTokenResponse;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.AuthenticationException;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class AuthController {
    private final ResponseErrorValidation responseErrorValidation;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;


    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult,
                                                   HttpServletRequest request) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try {
            var tokenResp = tokenService.generateToken(loginRequest);
            return ResponseEntity.ok(tokenResp);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JWTTokenResponse(false, "Token is expired"));
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest,
                                               BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))return errors;

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User Registered successfully"));
    }
}
