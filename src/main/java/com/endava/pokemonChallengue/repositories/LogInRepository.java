package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LogInRepository extends JpaRepository<UserInfo,Long> {

    //SELECT * FROM user WHERE email = ?
    @Query("SELECT u FROM UserInfo u WHERE u.email =?1 OR u.username =?2")
    Optional<UserInfo> findUserByEmailAndUsername(String email, String username);
    @Query("SELECT u FROM UserInfo u WHERE u.email =?1")
    UserInfo findByEmail(String email);

    @Query("SELECT u FROM UserInfo u WHERE u.email =?1 AND u.password=?2")
    Optional<UserInfo> findByEmailAndPassword(String email, String password);


}
