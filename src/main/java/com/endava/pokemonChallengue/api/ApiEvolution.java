package com.endava.pokemonChallengue.api;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiEvolution {
    ApiConnection apiConnection = new ApiConnection();
    String keyEvolves = "evolves_to";

    public Map<String, Object> findEvolution(String name) {
        Map<String, Object> evolutionBody = new LinkedHashMap<>();

        try {
            URL speciesURL = new URL("https://pokeapi.co/api/v2/pokemon-species/" + name);
            JSONObject pokemonSpecies = apiConnection.getJSON(speciesURL);

            String evolutionURL = pokemonSpecies.getJSONObject("evolution_chain").getString("url");
            JSONObject evolutionChain = apiConnection.getJSON(new URL(evolutionURL));
            JSONObject chain = evolutionChain.getJSONObject("chain");
            int evolutionSize = chain.getJSONArray(keyEvolves).length();

            if(evolutionSize == 1){
                Map<Integer, JSONObject> sequenceEvolution = sequenceEvolution(chain);
                evolutionBody.put("evolution-chain", sequenceEvolution);
                evolutionBody.put("next-evolution", nextEvolution(name, sequenceEvolution));
                return evolutionBody;
            } else{
                Map<Integer, JSONObject> branchEvolution = branchEvolution(chain, evolutionSize);
                evolutionBody.put("evolution-chain", branchEvolution);
                evolutionBody.put("next-evolution", "Depends on Pokemon environment");
                return evolutionBody;
            }
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException caught!");
            return new LinkedHashMap<>();
        }
    }

    public Map<Integer, JSONObject> sequenceEvolution(JSONObject chain){
        LinkedHashMap<Integer, JSONObject> sequenceEvolution = new LinkedHashMap<>();
        int exit = 1;
        int counter = 0;

        while(exit>0){
            sequenceEvolution.put(counter, buildSpecies(chain));
            chain = chain.getJSONArray(keyEvolves).getJSONObject(0);
            counter++;

            if(chain.getJSONArray(keyEvolves).length() == 0){
                sequenceEvolution.put(counter, buildSpecies(chain));
                exit--;
            }
        }
        return sequenceEvolution;
    }


    public Map<Integer, JSONObject> branchEvolution(JSONObject chain, int evolutionSize){
        LinkedHashMap<Integer, JSONObject> branchEvolution = new LinkedHashMap<>();

        branchEvolution.put(0, buildSpecies(chain));
        for(int i = 0; i < evolutionSize; i++){
            JSONObject evolutionChain = chain.getJSONArray(keyEvolves).getJSONObject(i);
            branchEvolution.put(i+1, buildSpecies(evolutionChain));
        }

        return branchEvolution;
    }

    public JSONObject nextEvolution(String name, Map<Integer, JSONObject> sequenceEvolution){
        for(int i = 0; i<sequenceEvolution.size(); i++){
            String evolutionName = sequenceEvolution.get(i).getString("name");
            if(name.equals(evolutionName)){
                return sequenceEvolution.get(i+1);
            }
        }
        return null;
    }

    public JSONObject buildSpecies(JSONObject chain){
        JSONObject speciesData = new JSONObject();
        String name = chain
                .getJSONObject("species")
                .getString("name");

        speciesData.put("name", name);
        speciesData.put("detailed_url", "/pokedex/pokemon/"+name);

        return speciesData;
    }
}
