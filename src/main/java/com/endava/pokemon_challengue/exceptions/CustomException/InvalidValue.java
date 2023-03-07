package com.endava.pokemon_challengue.exceptions.CustomException;

public class InvalidValue extends RuntimeException {
    public InvalidValue(String message) {
        super(message);
    }
}