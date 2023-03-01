package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.services.LogInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/pokedex/auth")
public class LogInController {

    private final LogInService logInService;

    public LogInController(LogInService logInService){this.logInService = logInService;}


    @PostMapping("/signIn")
    public ResponseEntity<Object> signUpUser(@RequestBody @NotNull @NotEmpty User user){

        return new ResponseEntity<>(logInService.addNewUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/logIn")
    public ResponseEntity<Object> logInUser(@RequestBody @NotNull @NotEmpty User user){
        return new ResponseEntity<>(logInService.logInUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/logOut")
    public ResponseEntity<Object> logOutUser(@RequestBody @NotNull @NotEmpty User user){
        return new ResponseEntity<>(logInService.logInUser(user), HttpStatus.CREATED);
    }
}
