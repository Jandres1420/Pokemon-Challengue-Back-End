package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon,Long> {

}
