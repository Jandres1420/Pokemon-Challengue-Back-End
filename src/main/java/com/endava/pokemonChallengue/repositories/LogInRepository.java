package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LogInRepository extends JpaRepository<User,Long> {

    //SELECT * FROM user WHERE email = ?
    @Query("SELECT u FROM User u WHERE u.email =?1 OR u.username =?2")
    Optional<User> findUserByEmailAndUsername(String email,String username);
    @Query("SELECT u FROM User u WHERE u.email =?1")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email =?1 AND u.password=?2")
    Optional<User> findByEmailAndPassword(String email, String password);


}
