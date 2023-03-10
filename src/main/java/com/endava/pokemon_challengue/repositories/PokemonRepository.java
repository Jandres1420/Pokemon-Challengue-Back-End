package com.endava.pokemon_challengue.repositories;

import com.endava.pokemon_challengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon,Long> {

    @Query("SELECT p FROM Pokemon p WHERE p.name= :name")
    Optional<Pokemon> findPokemonByName(@Param("name") String name);

}
