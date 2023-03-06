package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Capture;
import com.endava.pokemonChallengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CaptureRepository extends JpaRepository<Capture, Long> {
    @Query("SELECT c FROM Capture c WHERE c.pokemon=?1")
    Optional<Capture> findCaptureByPokemon(Pokemon pokemon);
}
