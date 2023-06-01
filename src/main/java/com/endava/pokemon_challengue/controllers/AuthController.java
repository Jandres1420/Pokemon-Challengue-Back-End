package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.ForgotPassword.ForgotPasswordDTO;
import com.endava.pokemon_challengue.models.dto.requestBody.LogInDto;
import com.endava.pokemon_challengue.models.dto.requestBody.SignUpDto;
import com.endava.pokemon_challengue.models.dto.responseBody.ForgotPasswordResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.LogInResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.LogOutResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SignUpResponse;
import com.endava.pokemon_challengue.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The user was correctly register",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignUpDto.class))}),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content),

            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content) })

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponse> signUpUser(@RequestBody @Valid @NotNull @NotEmpty SignUpDto signUpDto){
        return new ResponseEntity<>(authService.signUp(signUpDto), HttpStatus.CREATED);
    }

    @PostMapping("/logIn")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LogInResponse> logInUser(@RequestBody @Valid @NotNull @NotEmpty LogInDto logInDto){
        return new ResponseEntity<>(authService.logInUser(logInDto), HttpStatus.ACCEPTED);
    }

    @PostMapping("/forgotPassword")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody @Valid @NotNull @NotEmpty ForgotPasswordDTO forgotPasswordDTO){
        return new ResponseEntity<>(authService.forgotPassword(forgotPasswordDTO), HttpStatus.ACCEPTED);
    }

    @PutMapping("/logOut")
    public ResponseEntity<LogOutResponse> logOutUser(@RequestBody @Valid @NotNull @NotEmpty LogInDto LogInDto){
        return new ResponseEntity<>(authService.logOutUser(LogInDto), HttpStatus.ACCEPTED);
    }
}

