package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

public class APIEvolutionWorking {
    APIConnectionURL apiConnectionURL = new APIConnectionURL();

    public void findEvolution(String name) {
        LinkedHashMap<Integer, Object> evolution_body = new LinkedHashMap<>();

        try {
            URL species_url = new URL("https://pokeapi.co/api/v2/pokemon-species/" + name);
            JSONObject pokemon_species = apiConnectionURL.getJSON(species_url);

            String evolution_url = pokemon_species.getJSONObject("evolution_chain").getString("url");
            JSONObject evolution_chain = apiConnectionURL.getJSON(new URL(evolution_url));
            JSONObject chain = evolution_chain.getJSONObject("chain");
            int evolution_size = chain.getJSONArray("evolves_to").length();

            if(evolution_size == 1){
                //Sequence Evolutions
                int exit = 1;

                while(exit>0){
                    System.out.println(chain.getJSONObject("species"));
                    JSONArray evolution = chain.getJSONArray("evolves_to");
                    chain = evolution.getJSONObject(0);

                    if(chain.getJSONArray("evolves_to").length() == 0){
                        System.out.println(chain.getJSONObject("species"));
                        exit--;
                    }
                }
            }else{
                //Branch Evolutions
                System.out.println(chain.getJSONObject("species"));
                for(int i = 0; i < evolution_size; i++){
                    JSONObject evolution = chain
                            .getJSONArray("evolves_to")
                            .getJSONObject(i)
                            .getJSONObject("species");
                    System.out.println(evolution);
                }
            }

        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException caught!");
        }
    }
}
