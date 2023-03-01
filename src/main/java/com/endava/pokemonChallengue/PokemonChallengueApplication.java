package com.endava.pokemonChallengue;

import com.endava.pokemonChallengue.api.APIPokemonSpecies;
import com.endava.pokemonChallengue.api.APIEvolution;
import com.endava.pokemonChallengue.api.APIDashboard;
import com.endava.pokemonChallengue.api.APIPokemon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class PokemonChallengueApplication {

	public static void main(String[] args) throws MalformedURLException {
		SpringApplication.run(PokemonChallengueApplication.class, args);
		APIPokemon apiPokemon = new APIPokemon();
		APIPokemonSpecies apiPokemonSpecies = new APIPokemonSpecies();
		APIEvolution apiEvolution = new APIEvolution();
		APIDashboard apiDashboard = new APIDashboard();

		String name = "pikachu";

		//S12 Pokedex Dashboard
		//apiDashboard.showDashBoard(5,5);

		/*S13 Single Pokemon Details
		Map<String, Object> pokemonDetails = new LinkedHashMap<>();
		pokemonDetails.put("name", apiPokemon.getJSON(name).getString("name"));
		pokemonDetails.put("type", apiPokemon.getPokemonType(name));
		pokemonDetails.put("img-path", apiPokemon.getPokemonImage(name));
		pokemonDetails.put("description", apiPokemonSpecies.getDescription(name));
		pokemonDetails.put("stats", apiPokemon.getPokemonStats(name));
		pokemonDetails.put("abilities", apiPokemon.getPokemonAbilities(name, "en"));
		System.out.println(pokemonDetails);*/

		//S14 See Next Evolution
		System.out.println(apiEvolution.findEvolution(name));



	}
}


