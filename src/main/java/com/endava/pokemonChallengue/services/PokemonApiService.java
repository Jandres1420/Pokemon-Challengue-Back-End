package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.api.ApiDashboard;
import com.endava.pokemonChallengue.api.ApiEvolution;
import com.endava.pokemonChallengue.api.ApiPokemonDetails;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

@Service
public class PokemonApiService {

    public Object getDashboard(Integer quantity, Integer offset){
        ApiDashboard apiDashboard = new ApiDashboard();
        return apiDashboard.showDashBoard(quantity, offset);
    }

    public Object getPokemonDetails(String value, String language) throws MalformedURLException {
        ApiPokemonDetails apiPokemonDetails = new ApiPokemonDetails();
        return apiPokemonDetails.getPokemonDetails(value, language);
    }

    public Object getEvolution(String value){
        ApiEvolution apiEvolution = new ApiEvolution();
        return apiEvolution.findEvolution(value);
    }
}
