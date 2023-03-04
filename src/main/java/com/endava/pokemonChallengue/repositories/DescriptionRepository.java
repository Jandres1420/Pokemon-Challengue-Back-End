package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

}
