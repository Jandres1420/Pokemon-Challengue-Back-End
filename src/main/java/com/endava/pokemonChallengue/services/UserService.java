package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.repositories.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Object addNewUser(User user) {
        Optional<User> optionalUserEmail = userRepository.findUserByEmail(user.getEmail(), user.getUsername());
        signInExceptions(optionalUserEmail,user);
        userRepository.save(user);
        Map<String, Object> filteringUser = new HashMap<>();
        filteringUser.put("id",userRepository.findByEmail(user.getEmail()).getId());
        filteringUser.put("email",user.getEmail());
        filteringUser.put("username",user.getUsername());
        return filteringUser;

    }

    public void signInExceptions(Optional<User> optionalUserEmail,User user) {

        if (user.getEmail() == null || user.getUsername() == null) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Fields email or username not null");
        } else if (optionalUserEmail.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_VALUE, "Email already in use or Username already in use");
        } else if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid email");
        }
    }



}
