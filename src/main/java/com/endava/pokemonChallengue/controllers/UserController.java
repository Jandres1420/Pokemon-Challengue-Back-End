package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pokedex/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> signUpUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addNewUser(user), HttpStatus.CREATED);
    }


}
