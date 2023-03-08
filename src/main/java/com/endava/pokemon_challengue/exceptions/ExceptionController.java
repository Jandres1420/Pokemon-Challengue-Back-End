package com.endava.pokemon_challengue.exceptions;

import com.endava.pokemon_challengue.exceptions.CustomException.DuplicateValue;
import com.endava.pokemon_challengue.exceptions.CustomException.InvalidRole;
import com.endava.pokemon_challengue.exceptions.CustomException.InvalidValue;
import com.endava.pokemon_challengue.exceptions.CustomException.ParamsRequired;
import com.endava.pokemon_challengue.exceptions.Response.UnauthorizedResponse;
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

    @ExceptionHandler(ParamsRequired.class)
    public ResponseEntity<ExceptionResponse> handleParamsRequired(ParamsRequired exception,
                                                                  WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidValue.class)
    public ResponseEntity<ExceptionResponse> handleInvalidValue(InvalidValue exception,
                                                                WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRole.class)
    public ResponseEntity<UnauthorizedResponse> handleInvalidRole(InvalidRole exception,
                                                                  WebRequest request) {
        UnauthorizedResponse response = new UnauthorizedResponse();
        response.setResponseMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}