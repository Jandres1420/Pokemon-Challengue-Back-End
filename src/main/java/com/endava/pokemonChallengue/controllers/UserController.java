package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping(path = "/pokedex/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> signUpUser(@RequestBody @NotNull @NotEmpty User user){

        return new ResponseEntity<>(userService.addNewUser(user), HttpStatus.CREATED);
    }


}
