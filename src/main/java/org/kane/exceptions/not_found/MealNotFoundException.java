package org.kane.exceptions.not_found;

public class MealNotFoundException extends RuntimeException{
    public MealNotFoundException(String message) {
        super(message);
    }
}
