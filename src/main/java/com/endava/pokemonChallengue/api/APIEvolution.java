package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Objects;

public class APIEvolution {
    APIConnectionURL apiConnectionURL = new APIConnectionURL();

    public  LinkedHashMap<Integer, Object> findEvolution(String name) {
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
                int counter = 0;
                String species_name = "";
                String species_detailed_url="";

                while(exit>0){
                    LinkedHashMap<String, String> species_data = new LinkedHashMap<>();
                    species_name = chain.getJSONObject("species").getString("name");
                    species_detailed_url = chain.getJSONObject("species").getString("url");

                    species_data.put("name", species_name);
                    species_data.put("detailed_url", species_detailed_url);

                    evolution_body.put(counter, species_data);

                    JSONArray evolution = chain.getJSONArray("evolves_to");
                    chain = evolution.getJSONObject(0);
                    counter++;

                    if(chain.getJSONArray("evolves_to").length() == 0){
                        LinkedHashMap<String, String> final_species_data = new LinkedHashMap<>();
                        species_name = chain.getJSONObject("species").getString("name");
                        species_detailed_url = chain.getJSONObject("species").getString("url");

                        final_species_data.put("name", species_name);
                        final_species_data.put("detailed_url", species_detailed_url);

                        evolution_body.put(counter, species_data);
                        exit--;
                    }
                }
                return evolution_body;
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
        return null;
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException caught!");
            return null;
        }
    }
}
