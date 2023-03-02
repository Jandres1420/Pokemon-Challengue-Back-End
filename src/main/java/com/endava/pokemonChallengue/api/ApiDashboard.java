package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiDashboard {
    ApiConnection apiConnection = new ApiConnection();

    public Map<String, Object> showDashBoard(int quantity, int offset) {

        try {
            URL dashboardURL = new URL("https://pokeapi.co/api/v2/pokemon?limit="+quantity+"&offset="+offset);
            JSONArray dashboard = apiConnection
                    .getJSON(dashboardURL)
                    .getJSONArray("results");

            Map<String, Object> pokedex = new LinkedHashMap<>();

            for(int i=0; i<dashboard.length(); i++) {
                String pokemonName = dashboard.getJSONObject(i).getString("name");
                ApiPokemon apiPokemon = new ApiPokemon();

                JSONObject pokemon = apiPokemon.getJSON(pokemonName);

                Map<String, Object> pokemonData = new LinkedHashMap<>();
                pokemonData.put("name", pokemon.getString("name"));
                pokemonData.put("id", pokemon.getInt("id"));
                pokemonData.put("type", apiPokemon.getPokemonType(pokemonName));
                pokemonData.put("img-path", apiPokemon.getPokemonImage(pokemonName));

                pokedex.put(String.valueOf(i),pokemonData);
            }

            return pokedex;

        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException caught!");
            return new LinkedHashMap<>();
        }
    }
}
