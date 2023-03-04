package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Role;
import com.endava.pokemonChallengue.models.UserInfo;
import com.endava.pokemonChallengue.repositories.LogInRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LogInServiceTest {
    @Mock
    private final LogInRepository logInRepository;
    @InjectMocks
    private LogInService logInService;

    private UserInfo userInfo;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this);
        userInfo = new UserInfo();
        userInfo.setName("Andres");
        userInfo.setPassword("hola123");
        userInfo.setUsername("Jandres1420");
        userInfo.setEmail("juan.pico@endava.com");
        userInfo.setRole(Role.ADMIN);
    }

    public LogInServiceTest(LogInRepository logInRepository){
        this.logInRepository = logInRepository;
    }

    @Test
    void signInExceptions() {
    }
}