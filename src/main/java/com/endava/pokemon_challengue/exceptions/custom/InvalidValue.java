package com.endava.pokemon_challengue.exceptions.custom;

public class InvalidValue extends RuntimeException {
    public InvalidValue(String message) {
        super(message);
    }
}