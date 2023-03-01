package com.endava.pokemonChallengue.exceptions.CustomException;

public class InvalidValue extends RuntimeException {
    public InvalidValue(String message) {
        super(message);
    }
}