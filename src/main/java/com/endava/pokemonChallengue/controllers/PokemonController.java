package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.services.PokemonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

        pokemonApiService.pokemonService(pokemonDTO, pokemonSpeciesDTO);
    }
}
