package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.Cure;
import com.endava.pokemonChallengue.models.dto.responseBody.ResponseDoctorDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
import com.endava.pokemonChallengue.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pokedex")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    /*
    @GetMapping("/pokemon-trainer/{trainerUsername}/pokemon")
    @ResponseStatus(HttpStatus.OK)
    public SeePokemonOakResponseDto seeAllPokemonsProfessorOakAllParams(@PathVariable String trainerUsername,
                                                                        @RequestParam int quantity,
                                                                        @RequestParam int offset,
                                                                        @RequestHeader(value = "usernameRole") String usernameRole){
        return roleService.seeAllPokemonsProfessorOakAllParams(trainerUsername,quantity,offset,usernameRole);
    }

    @GetMapping("/pokemon-trainer/{trainerUsername}/pokemon")
    public SeePokemonOakResponseDto seeAllPokemonsProfessorOakQuantity(
            @PathVariable String trainerUsername,
            @RequestParam int quantity,
            @RequestHeader(value = "usernameRole") String usernameRole){
        return roleService.seeAllPokemonsProfessorOakQuantity(trainerUsername,quantity,usernameRole);
    }

    @PostMapping("/pokemon/cure/{capture_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDoctorDto curePokemonDoctor(@PathVariable String trainerUsername,
                                               @RequestParam int pokemonId,
                                               @RequestHeader(value = "usernameRole") String usernameRole,
                                               @RequestBody Cure cure){
        return roleService.curePokemonDoctor(trainerUsername,pokemonId,usernameRole,cure);
    }

    @PostMapping("/pokemon-trainer/{trainerUsername}/zero-health")
    @ResponseStatus(HttpStatus.OK)
    public void zeroHealth(@PathVariable String trainerUsername, @RequestParam int pokemonId,
                           @RequestHeader(value = "usernameRole") String usernameRole){
        roleService.reducePokemonHealth(trainerUsername,pokemonId,usernameRole);
    }*/

}
