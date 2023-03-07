package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.UserInfo;

import com.endava.pokemonChallengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemonChallengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemonChallengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
import com.endava.pokemonChallengue.models.dto.responseBody.ResponseDoctorDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemonChallengue.repositories.UserRepository;
import com.endava.pokemonChallengue.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pokedex")
public class RoleController {

    private final RoleService roleService;
    private final UserRepository userRepository;



    @GetMapping("/pokemon-trainer/{username}/pokemon")
    @ResponseStatus(HttpStatus.OK)
    public SeePokemonFromTrainerDto seePokemonsFromTrainer(@PathVariable String username,
                                                           @RequestParam int quantity,
                                                           @RequestParam int offset,
                                                           @RequestHeader(value = "usernameAsking") String usernameAsking,
                                                           @RequestHeader(value = "sortBy", defaultValue = "default value") String sortBy,
                                                           @RequestHeader(value = "filterByType",  defaultValue = "default value") String type){

        return roleService.seePokemonFromTrainer(username,quantity,offset,usernameAsking,type,sortBy);
    }

    @PostMapping("/pokemon/cure/{capture_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDoctorDto curePokemonDoctor(@PathVariable Long capture_id,
                                               @RequestHeader(value = "usernameRole") String usernameRole){
        return roleService.curePokemonDoctor(capture_id,usernameRole);
    }

    @PostMapping("/hola")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse curePokemonDoctor(@PathVariable Long capture_id,
                                             @RequestHeader(value = "usernameRole") String usernameRole){
        return roleService.curePokemonDoctor(capture_id,usernameRole);
    }


    @PostMapping("/pokemon-trainer/{trainerUsername}/zero-health")
    @ResponseStatus(HttpStatus.OK)
    public void zeroHealth(@PathVariable String trainerUsername, @RequestParam int pokemonId,
                           @RequestHeader(value = "usernameRole") String usernameRole){
        roleService.reducePokemonHealth(trainerUsername,pokemonId,usernameRole);
    }


    @PostMapping("/pokemon-trainer/{trainerToFollow}/relationship")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse followAndUnfollowTrainer(@PathVariable String trainerToFollow, @RequestBody FollowRequest followRequest,
                            @RequestHeader(value = "trainer") String trainer){
        return roleService.followAndUnfollowTrainer(trainerToFollow,followRequest,trainer);
    }

    @PostMapping("/admin/changeRole")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse administrateProfiles(@RequestBody AdminRoleChange adminRoleChange,
                            @RequestHeader(value = "admin") String admin){

        return roleService.administrateProfiles(adminRoleChange,admin);
    }

}
