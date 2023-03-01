package com.endava.pokemonChallengue.exceptions.CustomException;

public class ParamsRequired extends RuntimeException {
    public ParamsRequired(String message) {
        super(message);
    }
}
