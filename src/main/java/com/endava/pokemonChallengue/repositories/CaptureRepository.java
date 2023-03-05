package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Capture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptureRepository extends JpaRepository<Capture, Long> {
}
