package com.endava.pokemon_challengue.repositories;

import com.endava.pokemon_challengue.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

    @Query("SELECT u FROM UserProfile u WHERE u.email =?1 OR u.username =?2")
    Optional<UserProfile> findUserByEmailAndUsername(String email, String username);

    @Query("SELECT u FROM UserProfile u WHERE u.email =?1")
    UserProfile findByEmail(String email);

    @Query("SELECT u FROM UserProfile u WHERE u.email =?1 AND u.password=?2")
    Optional<UserProfile> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM UserProfile u WHERE u.username =?1")
    Optional<UserProfile> findByUsername(String username);

}
