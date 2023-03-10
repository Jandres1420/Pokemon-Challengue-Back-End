package com.endava.pokemon_challengue.exceptions;

import com.endava.pokemon_challengue.exceptions.custom.*;
import com.endava.pokemon_challengue.exceptions.response.UnauthorizedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicateValue.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateEntityException(DuplicateValue exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParamsRequired.class)
    public ResponseEntity<ExceptionResponse> handleParamsRequired(ParamsRequired exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidValue.class)
    public ResponseEntity<ExceptionResponse> handleInvalidValue(InvalidValue exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRole.class)
    public ResponseEntity<UnauthorizedResponse> handleInvalidRole(InvalidRole exception) {
        UnauthorizedResponse response = new UnauthorizedResponse();
        response.setResponseMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFound exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}