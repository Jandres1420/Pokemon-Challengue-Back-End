package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon,Long> {
}
