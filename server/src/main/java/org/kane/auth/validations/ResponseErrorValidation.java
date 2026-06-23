package org.kane.auth.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseErrorValidation {
    public ResponseEntity<Object> mapValidationService(BindingResult result) {
        if(result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            if(!CollectionUtils.isEmpty(result.getAllErrors())) {
                result.getAllErrors().forEach((error) -> errors.put(error.getCode(), error.getDefaultMessage()));
            }
            result.getFieldErrors().forEach((fieldError) -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
