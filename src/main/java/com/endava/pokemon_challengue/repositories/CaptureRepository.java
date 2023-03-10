package com.endava.pokemon_challengue.repositories;

import com.endava.pokemon_challengue.models.Capture;
import com.endava.pokemon_challengue.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CaptureRepository extends JpaRepository<Capture, Long> {

    @Query("SELECT c FROM Capture c WHERE c.user.user_id= :user_id AND c.capture_id = :capture_id")
    Optional<Capture> findCaptureByCaptureIdAndUserId(@Param("capture_id") Long capture_id,
                                                      @Param("user_id") int user_id);

    @Query("SELECT c FROM Capture c WHERE c.capture_id = :capture_id")
    Optional<Capture> findCaptureByCaptureId(@Param("capture_id") Long capture_id);

}
