package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon,Long> {

    @Query("SELECT p FROM Pokemon p WHERE p.pokemon_id = :pokemon_id OR p.name= :name")
    Optional<Pokemon> findPokemonByNameOrId(@Param("pokemon_id") int pokemon_id,
                                            @Param("name") String name);

    @Query("SELECT p FROM Pokemon p WHERE p.pokemon_id = :pokemon_id")
    Optional<Pokemon> findPokemonById(@Param("pokemon_id") int pokemon_id);


    @Query("SELECT p FROM Pokemon p WHERE p.name= :name")
    Optional<Pokemon> findPokemonByName(@Param("name") String name);


    /*
    @Query("SELECT p FROM Pokemon p WHERE p.pokemon_id=?1 OR p.name=?2")
    Optional<Pokemon> findPokemon(int pokemon, String name);

    @Query("SELECT p FROM Pokemon p WHERE p.pokemon_id=?1")
    Optional<Pokemon> findPokemonById(int pokemon);

    @Query("SELECT p FROM Pokemon p WHERE p.name=?1")
    Optional<Pokemon> findPokemonByName(String name);*/

}
