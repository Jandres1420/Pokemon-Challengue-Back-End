package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.repositories.UserRepository;
import com.endava.pokemon_challengue.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/pokemon/cure/{captureId}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse curePokemonDoctor(@PathVariable Long captureId,
                                               @RequestHeader(value = "usernameRole") String usernameRole){
        return roleService.curePokemonDoctor(captureId,usernameRole);
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
