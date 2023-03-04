package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Role;
import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.repositories.LogInRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LogInServiceTest {
    @Mock
    private final LogInRepository logInRepository;
    @InjectMocks
    private LogInService logInService;

    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this);
        user = new User();
        user.setName("Andres");
        user.setPassword("hola123");
        user.setUsername("Jandres1420");
        user.setEmail("juan.pico@endava.com");
        user.setRole(Role.ADMIN);
    }

    public LogInServiceTest(LogInRepository logInRepository){
        this.logInRepository = logInRepository;
    }

    @Test
    void signInExceptions() {
    }
}