package com.endava.pokemonChallengue.repositories;

import com.endava.pokemonChallengue.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
