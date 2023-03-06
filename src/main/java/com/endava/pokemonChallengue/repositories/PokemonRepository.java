package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon,Long> {

    @Query("SELECT p FROM Pokemon p WHERE p.pokemon_id=?1 OR p.name=?2")
    Optional<Pokemon> findPokemon(int pokemon, String name);


    @Query("SELECT p FROM Pokemon p WHERE p.pokemon_id=?1")
    Optional<Pokemon> findPokemonByPokemon_id(int pokemon_id);



    @Query("SELECT p FROM Pokemon p WHERE p.name=?1")
    Optional<Pokemon> findPokemonByName(String name);

}
