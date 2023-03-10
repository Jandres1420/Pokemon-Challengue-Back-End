package com.endava.pokemon_challengue.repositories;

import com.endava.pokemon_challengue.models.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {
}
