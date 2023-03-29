package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.generalType.PokemonTypesDTO;
import com.endava.pokemon_challengue.models.dto.generalType.TypesNameDTO;
import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.IndividualPokemonFromTrainerDto;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pokedex")
public class RoleController {

    private final RoleService roleService;
    private final RestTemplate restTemplate;
    @GetMapping("/pokemon-trainer/{username}/pokemon")
    @ResponseStatus(HttpStatus.OK)
    public SeePokemonFromTrainerDto seePokemonsFromTrainer(@PathVariable String username,
                                                           @RequestParam int quantity,
                                                           @RequestParam int offset,
                                                           @RequestParam String language,
                                                           @RequestHeader(value = "connected") String connected,
                                                           @RequestHeader(value = "sortBy", defaultValue = "default value") String sortBy,
                                                           @RequestHeader(value = "filterByType",  defaultValue = "default value") String type){

        SeePokemonFromTrainerDto seePokemonFromTrainerDto = roleService.seePokemonFromTrainer(username,quantity,offset,connected,type,sortBy);
        seePokemonFromTrainerDto.setLanguage(language);
        for(IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto : seePokemonFromTrainerDto.getResults()){
            List<String> languageType = new ArrayList<>();
            for(String lanType : individualPokemonFromTrainerDto.getTypes()){
                languageType.add(typeInLanguage(lanType,language));
            }
            individualPokemonFromTrainerDto.setTypesInLanguage(languageType);
        }
        return seePokemonFromTrainerDto;
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
    public PokemonTypesDTO getPokemonTypesDTO(String pokemonType){
        String urlSpecies = "https://pokeapi.co/api/v2/type/"+pokemonType;
        return restTemplate.getForObject(urlSpecies, PokemonTypesDTO.class);
    }

    public String typeInLanguage(String type, String language){
        PokemonTypesDTO pokemonTypesDTO = getPokemonTypesDTO(type);
        List<TypesNameDTO> filterPokemon = pokemonTypesDTO.getNames()
                .stream()
                .filter(p -> p.getLanguage().getName().contains(language))
                .collect(Collectors.toList());
        return filterPokemon.get(0).getName();
    }
}
