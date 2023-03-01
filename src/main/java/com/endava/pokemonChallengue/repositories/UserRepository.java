package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    //SELECT * FROM user WHERE email = ?
    @Query("SELECT u FROM User u WHERE u.email =?1 OR u.username =?2")
    Optional<User> findUserByEmail(String email,String username);
    @Query("SELECT u FROM User u WHERE u.email =?1")
    User findByEmail(String email);

}
