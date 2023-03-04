package com.endava.pokemonChallengue.api;

import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiPokemonDetails {
    ApiPokemon apiPokemon = new ApiPokemon();
    ApiPokemonSpecies apiPokemonSpecies = new ApiPokemonSpecies();

    public Object getPokemonDetails(String value, String languageCode) throws MalformedURLException {
        Map<String, Object> pokemonDetails = new LinkedHashMap<>();
        pokemonDetails.put("name", apiPokemon.getJSON(value).getString("name"));
        pokemonDetails.put("type", apiPokemon.getPokemonType(value));
        pokemonDetails.put("img-path", apiPokemon.getPokemonImage(value));
        pokemonDetails.put("description", apiPokemonSpecies.getDescription(value));
        pokemonDetails.put("stats", apiPokemon.getPokemonStats(value));
        pokemonDetails.put("abilities", apiPokemon.getPokemonAbilities(value, languageCode));
        return pokemonDetails;
    }
}
