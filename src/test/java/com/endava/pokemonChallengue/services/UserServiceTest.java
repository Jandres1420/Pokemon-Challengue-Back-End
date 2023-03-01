package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Role;
import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private final UserRepository userRepository;
    @InjectMocks
    private UserService userService;

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

    public UserServiceTest(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Test
    void addNewValidUser() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertNotNull(userService.addNewUser(user));
    }

    @Test
    void signInExceptions() {
    }
}