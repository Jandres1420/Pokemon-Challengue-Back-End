package com.endava.pokemon_challengue.repositories;

import com.endava.pokemon_challengue.models.Pokemon;
import com.endava.pokemon_challengue.models.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Long> {
}
