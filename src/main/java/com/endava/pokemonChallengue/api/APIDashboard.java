package com.endava.pokemonChallengue.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class APIDashboard {
    APIConnectionURL apiConnectionURL = new APIConnectionURL();

    public void showDashBoard(int quantity, int offset) {

        try {
            URL dashboard_url = new URL("https://pokeapi.co/api/v2/pokemon?limit="+quantity+"&offset="+offset);
            JSONArray dashboard = apiConnectionURL
                    .getJSON(dashboard_url)
                    .getJSONArray("results");

            HashMap<String, Object> pokedex = new HashMap<>();

            for(int i=0; i<dashboard.length(); i++) {
                String pokemon_name = dashboard.getJSONObject(i).getString("name");
                APIPokemon APIPokemon = new APIPokemon();

                JSONObject pokemon = APIPokemon.getJSON(pokemon_name);

                HashMap<String, Object> pokemon_data = new HashMap<>();
                pokemon_data.put("name", pokemon.getString("name"));
                pokemon_data.put("id", pokemon.getInt("id"));
                pokemon_data.put("type", APIPokemon.getPokemonType(pokemon_name));
                pokemon_data.put("img-path", APIPokemon.getPokemonImage(pokemon_name));

                pokedex.put(String.valueOf(i),pokemon_data);
            }

            System.out.println(pokedex);

        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException caught!");
        }
    }
}
