package org.kane.exceptions.not_found;

public class MealItemNotFoundException extends RuntimeException {
    public MealItemNotFoundException(String message) {
        super(message);
    }
}
