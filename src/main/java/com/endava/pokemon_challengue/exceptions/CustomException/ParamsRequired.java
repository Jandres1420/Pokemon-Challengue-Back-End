package com.endava.pokemon_challengue.exceptions.CustomException;

public class ParamsRequired extends RuntimeException {
    public ParamsRequired(String message) {
        super(message);
    }
}
