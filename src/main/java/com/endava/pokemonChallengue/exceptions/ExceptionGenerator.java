package com.endava.pokemonChallengue.exceptions;

import com.endava.pokemonChallengue.exceptions.CustomException.DuplicateValue;
import com.endava.pokemonChallengue.exceptions.CustomException.InvalidValue;
import com.endava.pokemonChallengue.exceptions.CustomException.ParamsRequired;
import org.springframework.stereotype.Component;
import com.endava.pokemonChallengue.exceptions.ExceptionResponse.*;
@Component
public class ExceptionGenerator {
    public static RuntimeException getException(ExceptionType type, String message) {
        switch (type) {
            case DUPLICATE_VALUE:
                return new DuplicateValue(message);
            case PARAMS_REQUIRED:
                return new ParamsRequired(message);
            case INVALID_VALUE:
                return new InvalidValue(message);
            default:
                return new RuntimeException(message);
        }
    }
}