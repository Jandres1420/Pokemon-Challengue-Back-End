package com.endava.pokemon_challengue.exceptions.CustomException;

public class InvalidRole extends RuntimeException {
    public InvalidRole(String message) {
        super(message);
    }
}
