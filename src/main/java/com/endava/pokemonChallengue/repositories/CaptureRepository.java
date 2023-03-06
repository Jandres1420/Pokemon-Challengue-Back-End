package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.Capture;
import com.endava.pokemonChallengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CaptureRepository extends JpaRepository<Capture, Long> {

    @Query("SELECT c FROM Capture c WHERE c.pokemon= :pokemon")
    Optional<Capture> findCaptureByPokemon(@Param("pokemon") Pokemon pokemon);


    @Query("SELECT c FROM Capture c WHERE c.user.user_id= :user_id AND c.capture_id = :capture_id")
    Optional<Capture> findCaptureByIdAndUsername(@Param("capture_id") Long capture_id,
                                                 @Param("user_id") int user_id);

    /*@Query("SELECT c FROM Capture c WHERE c.pokemon=?1")
    Optional<Capture> findCaptureByPokemon(Pokemon pokemon);*/
}
