package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.UserInfo;
import com.endava.pokemonChallengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemonChallengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemonChallengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
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


    @GetMapping("/pokemon-trainer/{trainerUsername}/pokemon")
    @ResponseStatus(HttpStatus.OK)
    public SeePokemonOakResponseDto seeAllPokemonsProfessorOakAllParams(@PathVariable String trainerUsername,
                                                                        @RequestParam int quantity,
                                                                        @RequestParam int offset,
                                                                        @RequestHeader(value = "usernameRole") String usernameRole,
                                                                        @RequestHeader(value = "filter",  defaultValue = "default value") String filter,
                                                                        @RequestHeader(value = "sort", defaultValue = "default value") String sort ){
        return roleService.seeAllPokemonsProfessorOakAllParams(trainerUsername,quantity,offset,usernameRole,filter,sort);
    }

    @PostMapping("/pokemon/cure/{capture_id}")
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
