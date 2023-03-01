package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class SinglePokemonDetails {
    APIManager apiManager = new APIManager();

    public void findPokemon(String value) {

        try {
            URL pokemon_url = new URL("https://pokeapi.co/api/v2/pokemon/"+value);
            JSONObject json = apiManager.getJSON(pokemon_url);

            //Name
            String name = json.getString("name");
            System.out.println("Name: "+name);

            //Stats
            String fullStats = "";
            JSONArray stats = json.getJSONArray("stats");

            for(int i=0; i<stats.length();i++){
                JSONObject stat = stats.getJSONObject(i).getJSONObject("stat");
                String statName = stat.getString("name");
                Integer statValue = stats.getJSONObject(i).getInt("base_stat");
                fullStats += statName + ": " + statValue+ ", ";
            }

            System.out.println("Stats: "+fullStats);

        }catch (NullPointerException e){
            System.err.println("NullPointerException caught!");
        }catch (MalformedURLException w){
            System.err.println("MalformedURLException caught!");
        }
    }
}
