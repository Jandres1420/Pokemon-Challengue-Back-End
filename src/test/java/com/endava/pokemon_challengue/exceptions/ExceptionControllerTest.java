package com.endava.pokemon_challengue.exceptions;

import com.endava.pokemon_challengue.exceptions.custom.*;
import com.endava.pokemon_challengue.exceptions.response.UnauthorizedResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerTest {

    @InjectMocks
    ExceptionController exceptionController;

    @Test
    void handleDuplicateEntityException() {
        DuplicateValue duplicateValue = new DuplicateValue("Duplicate");
        ResponseEntity<ExceptionResponse> responseEntity = exceptionController.handleDuplicateEntityException(duplicateValue);

        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    void handleParamsRequired() {
        ParamsRequired paramsRequired = new ParamsRequired("Params Required");
        ResponseEntity<ExceptionResponse> responseEntity = exceptionController.handleParamsRequired(paramsRequired);

        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    void handleInvalidValue() {
        InvalidValue invalidValue = new InvalidValue("Invalid Value");
        ResponseEntity<ExceptionResponse> responseEntity = exceptionController.handleInvalidValue(invalidValue);

        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    void handleInvalidRole() {
        InvalidRole invalidRole = new InvalidRole("Invalid Role");
        ResponseEntity<UnauthorizedResponse> responseEntity = exceptionController.handleInvalidRole(invalidRole);

        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 401);
    }

    @Test
    void handleNotFound() {
        NotFound notFound = new NotFound("Not Found");
        ResponseEntity<ExceptionResponse> responseEntity = exceptionController.handleNotFound(notFound);

        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }
}