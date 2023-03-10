package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.custom.DuplicateValue;
import com.endava.pokemon_challengue.exceptions.custom.InvalidValue;
import com.endava.pokemon_challengue.exceptions.custom.ParamsRequired;
import com.endava.pokemon_challengue.models.UserProfile;
import com.endava.pokemon_challengue.models.dto.requestBody.LogInDto;
import com.endava.pokemon_challengue.models.dto.requestBody.SignUpDto;
import com.endava.pokemon_challengue.models.dto.responseBody.LogInResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.LogOutResponse;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Captor
    ArgumentCaptor<UserProfile> userProfileArgumentCaptor;

    @Mock
    UserProfileRepository userProfileRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void Given_SignUpDto_When_SignUp_Then_CheckingItRegisterAPerson() {
        SignUpDto signUpDto = SignUpDto.builder()
                .email("gabriel@endava.com")
                .username("gabriel")
                .password("gabriel")
                .name("gabriel")
                .lastname("gabriel")
                .build();
        UserProfile profile = UserProfile.builder().email("gabriel@endava.com").build();
        authService.signUp(signUpDto);
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(userProfileArgumentCaptor.getValue().getEmail(),profile.getEmail());
    }

    @Test
    void Given_LogInDto_When_LogIn_Then_GiveMeThatTheUserIsConnected() {
        LogInDto logInDto = LogInDto.builder()
                .email("andres@endava.com")
                .password("andres")
                .build();
        when(userProfileRepository.findByEmailAndPassword(any(),any())).thenReturn(Optional.of(UserProfile.builder().email("andres@endava.com").password("andres").build()));
        LogInResponse logInResponse = authService.logInUser(logInDto);
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals("andres@endava.com",logInResponse.getEmail());
        Assertions.assertTrue(userProfileArgumentCaptor.getValue().getConnect());
    }
    @Test
    void Given_LogInDtoAlreadyConnected_When_LogIn_Then_GiveMeTheExceptionAlreadyConnected() {
        LogInDto logInDto = LogInDto.builder()
                .email("andres@endava.com")
                .password("andres")
                .build();
        Exception exceptionRepeatedEmail = Assertions.assertThrows(InvalidValue.class,()->{
            when(userProfileRepository.findByEmailAndPassword(any(),any())).thenReturn(Optional.of(UserProfile.builder().email("andres@endava.com").connect(true).password("andres").build()));
            LogInResponse logInResponse = authService.logInUser(logInDto);
            verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        });
        Assertions.assertEquals("The user is already connected", exceptionRepeatedEmail.getMessage());
    }

    @Test
    void Given_LogInDtoWithIncorrectCredentials_When_LogIn_Then_GiveMeException() {
        LogInDto logInDto = LogInDto.builder()
                .email("andres@endava.com")
                .password("andrsxs")
                .build();
        Exception exceptionRepeatedEmail = Assertions.assertThrows(InvalidValue.class,()->{
            authService.signUp(SignUpDto.builder().email("andres@endava.com").username("andres").password("andres").build());
            verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
            authService.logInUser(logInDto);
        });
        Assertions.assertEquals("The credentials are incorrect", exceptionRepeatedEmail.getMessage());
    }
    @Test
    void Given_LogInDto_When_LogOutUser_Then_SetTheUserOffline() {
        LogInDto logInDto = LogInDto.builder()
                .email("andres@endava.com")
                .password("andres")
                .build();
        when(userProfileRepository.findByEmailAndPassword(any(),any())).thenReturn(Optional.of(UserProfile.builder().email("andres@endava.com")
                .password("andres").connect(true).build()));
        LogOutResponse logOutResponse = authService.logOutUser(logInDto);
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals("ok",logOutResponse.getStatus());
    }

    @Test
    void Given_IncorrectInput_When_SignUpExceptions_Then_ThrowExceptionsParamsAndRepeatedEmail() {
       Exception exceptionEmail = Assertions.assertThrows(ParamsRequired.class,()->{
           authService.signUpExceptions(Optional.of(UserProfile.builder().build()),SignUpDto.builder().build());
       });
        Assertions.assertEquals("Fields email or username or role not null",exceptionEmail.getMessage());
        Exception exceptionRepeatedEmail = Assertions.assertThrows(DuplicateValue.class,()->{
            authService.signUp(SignUpDto.builder().email("andres@endava.com").username("andres").build());
            verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
            when(userProfileRepository.findUserByEmailAndUsername("andres@endava.com","andres"))
                    .thenReturn(Optional.of(UserProfile.builder().email("andres@endava.com").username("andres").build()));
            authService.signUp(SignUpDto.builder().email("andres@endava.com").username("andres").build());
        });
        Assertions.assertEquals("Email/username not available",exceptionRepeatedEmail.getMessage());
    }

    @Test
    void Given_IncorrectInput_When_SignUpExceptions_Then_ThrowExceptionsInvalidEmail() {
        Exception exceptionRepeatedEmail = Assertions.assertThrows(ParamsRequired.class,()->{
            authService.signUp(SignUpDto.builder().email("andres.endava.com").username("andres").build());
        });
        Assertions.assertEquals("Enter a valid email", exceptionRepeatedEmail.getMessage());
    }
}