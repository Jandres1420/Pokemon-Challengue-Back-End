package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addNewUser(User user) {
        System.out.println(user);
        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_EMAIL, "Email already in use");
        }return userRepository.save(user);

    }

}
