package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ApiPokemon {
    ApiConnection apiConnection = new ApiConnection();

    public JSONObject getJSON(String value) {
        try {
            URL pokemon_url = new URL("https://pokeapi.co/api/v2/pokemon/"+value);
            JSONObject pokemon = apiConnection.getJSON(pokemon_url);
            return pokemon;

        }catch (NullPointerException e){
            System.err.println("NullPointerException caught!");
            return null;
        }catch (MalformedURLException w){
            System.err.println("MalformedURLException caught!");
            return null;
        }
    }

    public HashMap<String, Object> getPokemonAbilities(String value, String languageCode) throws MalformedURLException {
        JSONArray abilities_chain = getJSON(value).getJSONArray("abilities");
        HashMap<String, Object> abilities = new HashMap<>();

        for (int i = 0; i < abilities_chain.length(); i++) {
            String name = "Name not available in "+languageCode;
            String description = "Description not available in "+languageCode;

            //Get the Ability JSON
            URL ability_url = new URL(abilities_chain
                    .getJSONObject(i)
                    .getJSONObject("ability")
                    .getString("url"));

            JSONArray ability_names = apiConnection.getJSON(ability_url).getJSONArray("names");
            JSONArray ability_effects = apiConnection.getJSON(ability_url).getJSONArray("effect_entries");

            for(int w=0; w<ability_names.length(); w++){
                String name_language = ability_names
                        .getJSONObject(w)
                        .getJSONObject("language")
                        .getString("name");
                if(name_language.equals(languageCode)){
                    name = ability_names.getJSONObject(w).getString("name");
                }
            }

            for(int z=0; z<ability_effects.length(); z++){
                String effect_language = ability_effects
                        .getJSONObject(z)
                        .getJSONObject("language")
                        .getString("name");
                if(effect_language.equals(languageCode)){
                    description = ability_effects
                            .getJSONObject(z)
                            .getString("effect")
                            .replace("\n", "")
                            .replace("\r", "");
                }
            }

            HashMap<String, Object> ability_data = new HashMap<>();

            ability_data.put("name", name);
            ability_data.put("description", description);

            abilities.put(String.valueOf(i),ability_data);
        }

        return abilities;
    }


    public ArrayList<String> getPokemonType(String value){
        ArrayList<String> types = new ArrayList<>();
        JSONArray types_chain = getJSON(value).getJSONArray("types");

        for(int i=0; i<types_chain.length();i++){
            String type = types_chain.getJSONObject(i).getJSONObject("type").getString("name");
            types.add(type);
        }
        return types;
    }

    public String getPokemonImage(String value){
        JSONObject sprites_chain = getJSON(value).getJSONObject("sprites");
        String img = sprites_chain
                .getJSONObject("other")
                .getJSONObject("dream_world")
                .getString("front_default");
        return img;
    }

    public HashMap<String, Integer> getPokemonStats(String value) {
        JSONArray stats_chain = getJSON(value).getJSONArray("stats");

        HashMap<String, Integer> stats = new HashMap<>();

        for (int i = 0; i < stats_chain.length(); i++) {
            JSONObject stat = stats_chain.getJSONObject(i).getJSONObject("stat");
            String statName = stat.getString("name");
            Integer statValue = stats_chain.getJSONObject(i).getInt("base_stat");

            stats.put(statName, statValue);
        }
        return stats;
    }


}
