package com.endava.pokemonChallengue.exceptions;

import com.endava.pokemonChallengue.exceptions.CustomException.DuplicateValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(DuplicateValue.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateEntityException(DuplicateValue exception,
                                                                            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}