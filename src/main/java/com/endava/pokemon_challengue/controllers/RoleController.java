package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pokedex")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/pokemon-trainer/{username}/pokemon")
    @ResponseStatus(HttpStatus.OK)
    public SeePokemonFromTrainerDto seePokemonsFromTrainer(@PathVariable String username,
                                                           @RequestParam int quantity,
                                                           @RequestParam int offset,
                                                           @RequestHeader(value = "connected") String connected,
                                                           @RequestHeader(value = "sortBy", defaultValue = "default value") String sortBy,
                                                           @RequestHeader(value = "filterByType",  defaultValue = "default value") String type){

        return roleService.seePokemonFromTrainer(username,quantity,offset,connected,type,sortBy);
    }

    @PostMapping("/pokemon/cure/{captureId}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse curePokemonDoctor(@PathVariable Long captureId,
                                             @RequestHeader(value = "connected") String connected){
        return roleService.curePokemonDoctor(captureId,connected);
    }

    @PostMapping("/pokemon-trainer/{trainerToFollow}/relationship")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse followAndUnfollowTrainer(@PathVariable String trainerToFollow,
                                                    @RequestBody FollowRequest followRequest,
                                                    @RequestHeader(value = "connected") String connected){

        return roleService.followAndUnfollowTrainer(trainerToFollow,followRequest,connected);
    }

    @PostMapping("/admin/changeRole")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse administrateProfiles(@RequestBody AdminRoleChange adminRoleChange,
                                                @RequestHeader(value = "connected") String connected){

        return roleService.administrateProfiles(adminRoleChange, connected);
    }
}
