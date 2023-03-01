package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class EvolutionChain {
    APIManager apiManager = new APIManager();

    public void findEvolution(String name) {

        try {
            URL species_url = new URL("https://pokeapi.co/api/v2/pokemon-species/" + name);
            JSONObject pokemon_species = apiManager.getJSON(species_url);

            String evolution_url = pokemon_species.getJSONObject("evolution_chain").getString("url");
            JSONObject evolution_chain = apiManager.getJSON(new URL(evolution_url));
            JSONObject chain = evolution_chain.getJSONObject("chain");

            System.out.println(chain);



        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException caught!");
        }
    }
}
