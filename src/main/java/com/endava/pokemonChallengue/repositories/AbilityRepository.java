package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Ability;
import com.endava.pokemonChallengue.models.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

}
