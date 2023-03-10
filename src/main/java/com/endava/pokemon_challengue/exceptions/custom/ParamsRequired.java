package com.endava.pokemon_challengue.exceptions.custom;

public class ParamsRequired extends RuntimeException {
    public ParamsRequired(String message) {
        super(message);
    }
}
