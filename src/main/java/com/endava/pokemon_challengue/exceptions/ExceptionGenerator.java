package com.endava.pokemon_challengue.exceptions;

import com.endava.pokemon_challengue.exceptions.CustomException.DuplicateValue;
import com.endava.pokemon_challengue.exceptions.CustomException.InvalidRole;
import com.endava.pokemon_challengue.exceptions.CustomException.InvalidValue;
import com.endava.pokemon_challengue.exceptions.CustomException.ParamsRequired;
import org.springframework.stereotype.Component;

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
            case INVALID_ROLE:
                return new InvalidRole(message);
            default:
                return new RuntimeException(message);
        }
    }
}