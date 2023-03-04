package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiPokemonSpecies {
    ApiConnection apiConnection = new ApiConnection();

    public JSONObject getJSON(String value) {
        try {
            URL pokemon_species_url = new URL("https://pokeapi.co/api/v2/pokemon-species/"+value);
            JSONObject pokemon_species = apiConnection.getJSON(pokemon_species_url);
            return pokemon_species;

        }catch (NullPointerException e){
            System.err.println("NullPointerException caught!");
            return null;
        }catch (MalformedURLException w){
            System.err.println("MalformedURLException caught!");
            return null;
        }
    }

    public String getDescription(String value) {
        JSONArray description_chain = getJSON(value).getJSONArray("flavor_text_entries");
        String description = description_chain
                .getJSONObject(0)
                .getString("flavor_text")
                .replace("\n", "")
                .replace("\r", "");

        return description;
    }
}
