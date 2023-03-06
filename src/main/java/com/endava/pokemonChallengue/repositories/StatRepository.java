package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("SELECT s FROM Stat s WHERE s.pokemon=?1")
    public Optional<Stat> findByHealth(Pokemon pokemon);
}
