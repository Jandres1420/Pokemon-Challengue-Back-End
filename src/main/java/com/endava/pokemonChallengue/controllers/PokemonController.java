package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.services.PokemonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PokemonController {

    private final RestTemplate restTemplate;
    private final PokemonApiService pokemonApiService;

    @Autowired
    public PokemonController(RestTemplate restTemplate, PokemonApiService pokemonApiService) {
        this.restTemplate = restTemplate;
        this.pokemonApiService = pokemonApiService;
    }

    @RequestMapping(path = "{language}/pokemon")
    @GetMapping()
    public void getPokemon(@PathVariable(name = "language") String language, @RequestParam String name) {
        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/"+name;
        PokemonDTO pokemonDTO = restTemplate.getForObject(urlPokemon, PokemonDTO.class);

        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/"+name;
        PokemonSpeciesDTO pokemonSpeciesDTO = restTemplate.getForObject(urlSpecies, PokemonSpeciesDTO.class);

        int abilitiesSize = pokemonDTO.getAbilities().size();
        List<AbilityDTO> abilities = new ArrayList<>();

        for(int i=0;i<abilitiesSize;i++){
            String urlAbility = pokemonDTO.getAbilities().get(i).getAbility().getUrl();
            abilities.add(restTemplate.getForObject(urlAbility, AbilityDTO.class));
        }

        pokemonApiService.pokemonService(pokemonDTO, pokemonSpeciesDTO, abilities, name);
    }
}
