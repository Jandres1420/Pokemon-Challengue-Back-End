package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.requestBody.LogInDto;
import com.endava.pokemon_challengue.models.dto.requestBody.SignUpDto;
import com.endava.pokemon_challengue.models.dto.responseBody.LogInResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.LogOutResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SignUpResponse;
import com.endava.pokemon_challengue.services.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthService authServiceMock;

    @InjectMocks
    AuthController authController;

    @Test
    void Given_LogInDTO_When_LogOutUser_Then_SuccessStatus() {
        when(authServiceMock.logOutUser(LogInDto.builder().email("gabriel").password("endava").build()))
                .thenReturn(LogOutResponse.builder().status("logged Out").build());

        ResponseEntity<LogOutResponse> responseEntity = authController.logOutUser(LogInDto.builder().email("gabriel").password("endava").build());
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 202);
    }

    @Test
    void Given_LogInDTO_When_LogInUser_Then_SuccessStatus() {
        when(authServiceMock.logInUser(LogInDto.builder().email("gabriel").password("endava").build()))
                .thenReturn(LogInResponse.builder().email("gabriel").build());

        ResponseEntity<LogInResponse> responseEntity = authController.logInUser(LogInDto.builder().email("gabriel").password("endava").build());
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 202);
    }

    @Test
    void Given_SignUpDTO_When_SignUpUser_Then_SuccessStatus() {
        when(authServiceMock.signUp(SignUpDto.builder().email("gabriel@endava.com").username("gabriel").build()))
                .thenReturn(SignUpResponse.builder().email("gabriel").build());

        ResponseEntity<SignUpResponse> responseEntity = authController.signUpUser(
                SignUpDto.builder().email("gabriel@endava.com").username("gabriel").build());
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 201);
    }
}