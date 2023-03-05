package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.UserInfo;
import com.endava.pokemonChallengue.models.dto.login.LogInDto;
import com.endava.pokemonChallengue.models.dto.login.LogOutDto;
import com.endava.pokemonChallengue.models.dto.login.SignInDto;
import com.endava.pokemonChallengue.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/pokedex/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){this.authService = authService;}


    @PostMapping("/signUp")
    public ResponseEntity<SignInDto> signUpUser(@RequestBody @Valid @NotNull @NotEmpty UserInfo userInfo){
        return new ResponseEntity<>(authService.signIn(userInfo), HttpStatus.CREATED);
    }

    @PostMapping("/logIn")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LogInDto> logInUser(@RequestBody @Valid @NotNull @NotEmpty UserInfo userInfo){
        return new ResponseEntity<>(authService.logInUser(userInfo), HttpStatus.ACCEPTED);
    }

    @PutMapping("/logOut")
    public ResponseEntity<LogOutDto> logOutUser(@RequestBody @Valid @NotNull @NotEmpty UserInfo userInfo){
        return new ResponseEntity<>(authService.logOutUser(userInfo), HttpStatus.ACCEPTED);
    }
}

