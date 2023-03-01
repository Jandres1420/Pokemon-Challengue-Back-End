package com.endava.pokemonChallengue;

import com.endava.pokemonChallengue.api.EvolutionChain;
import com.endava.pokemonChallengue.api.SinglePokemonDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class PokemonChallengueApplication {

	public static void main(String[] args) throws MalformedURLException {
		SpringApplication.run(PokemonChallengueApplication.class, args);
		SinglePokemonDetails singlePokemonDetails = new SinglePokemonDetails();
		EvolutionChain evolutionChain = new EvolutionChain();


		singlePokemonDetails.findPokemon("eevee");
		evolutionChain.findEvolution("eevee");
	}
}


