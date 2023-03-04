package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

}
