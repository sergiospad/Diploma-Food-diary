package org.kane.exceptions.not_found;

public class CookingStageNotFoundException extends RuntimeException {
    public CookingStageNotFoundException(String message) {
        super(message);
    }
}
