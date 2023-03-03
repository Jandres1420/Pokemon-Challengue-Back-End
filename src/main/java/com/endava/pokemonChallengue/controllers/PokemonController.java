package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.dto.FullPokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class PokemonController {

    private final RestTemplate restTemplate;

    @Autowired
    public PokemonController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(path = "{language}/test/pokemon")
    @GetMapping()
    public PokemonDTO getPokemonDetails(@PathVariable(name = "language") String language, @RequestParam String name) {
        String url = "https://pokeapi.co/api/v2/pokemon/"+name;
        PokemonDTO pokemonDTO = restTemplate.getForObject(url, PokemonDTO.class);
        return pokemonDTO;
    }

    @RequestMapping(path = "{language}/test/pokemon-species")
    @GetMapping()
    public PokemonSpeciesDTO getPokemonSpecies(@PathVariable(name = "language") String language, @RequestParam String name) {
        String url = "https://pokeapi.co/api/v2/pokemon-species/"+name;
        PokemonSpeciesDTO pokemonSpeciesDTO = restTemplate.getForObject(url, PokemonSpeciesDTO.class);
        return pokemonSpeciesDTO;
    }


    @RequestMapping(path = "{language}/pokemon")
    @GetMapping()
    public FullPokemonDTO getPokemon(@PathVariable(name = "language") String language, @RequestParam String name) {
        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/"+name;
        PokemonDTO pokemonDTO = restTemplate.getForObject(urlPokemon, PokemonDTO.class);

        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/"+name;
        PokemonSpeciesDTO pokemonSpeciesDTO = restTemplate.getForObject(urlSpecies, PokemonSpeciesDTO.class);

        FullPokemonDTO fullPokemonDTO = new FullPokemonDTO();

        fullPokemonDTO.setName(pokemonDTO.getName());
        fullPokemonDTO.setOrder(pokemonDTO.getOrder());
        fullPokemonDTO.setFlavor_text_entries(pokemonSpeciesDTO.getFlavor_text_entries());

        return fullPokemonDTO;
    }
}
