package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
import com.endava.pokemonChallengue.services.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pokedex")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }
    @GetMapping("/pokemon-trainer/{trainerUsername}/pokemon")
    public SeePokemonOakResponseDto seeAllPokemonsProfessorOak(@PathVariable String trainerUsername, @RequestParam int quantity,
                                                               @RequestParam int offset, @RequestHeader(value = "usernameRole") String usernameRole){
        System.out.println("trainer username " + trainerUsername +"\n quantity " + quantity +"\n offset " +offset+"\nusername rol  " +usernameRole);
        return roleService.seeAllPokemonsProfessorOak(trainerUsername,quantity,offset,usernameRole);
    }
}
