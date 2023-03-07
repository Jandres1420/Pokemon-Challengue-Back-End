package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.UserInfo;
import com.endava.pokemonChallengue.models.dto.responseBody.ResponseDoctorDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemonChallengue.repositories.UserRepository;
import com.endava.pokemonChallengue.services.RoleService;
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

    @PostMapping("/pokemon/cure/{capture_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDoctorDto curePokemonDoctor(@PathVariable Long capture_id,
                                               @RequestHeader(value = "usernameRole") String usernameRole){
        return roleService.curePokemonDoctor(capture_id,usernameRole);
    }

    @PostMapping("/hola")
    @ResponseStatus(HttpStatus.OK)
    public UserInfo test(){
        UserInfo userInfo = userRepository.findByUsername("camilo").get();
        // userInfo.getFollowers().add(userRepository.findByUsername("gsagsa").get());
//        userRepository.save(userInfo);
        return userInfo;
    }

    @PostMapping("/pokemon-trainer/{trainerUsername}/zero-health")
    @ResponseStatus(HttpStatus.OK)
    public void zeroHealth(@PathVariable String trainerUsername, @RequestParam int pokemonId,
                           @RequestHeader(value = "usernameRole") String usernameRole){
        roleService.reducePokemonHealth(trainerUsername,pokemonId,usernameRole);
    }

}
