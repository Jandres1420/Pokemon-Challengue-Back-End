package com.endava.pokemonChallengue.exceptions;

import com.endava.pokemonChallengue.exceptions.CustomException.DuplicateValue;
import org.springframework.stereotype.Component;

@Component
public class ExceptionGenerator {
    public static RuntimeException getException(ExceptionType type, String message) {
        switch (type) {
            case DUPLICATE_VALUE:
                return new DuplicateValue(message);
            default:
                return new RuntimeException(message);
        }
    }
}