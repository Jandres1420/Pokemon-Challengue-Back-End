package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.repositories.LogInRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LogInService {
    @Autowired
    private final LogInRepository logInRepository;

    @Autowired
    public LogInService(LogInRepository logInRepository){
        this.logInRepository = logInRepository;
    }

    public Object signIn(User user) {
        Optional<User> optionalUserEmail = logInRepository.findUserByEmailAndUsername(user.getEmail(), user.getUsername());
        signInExceptions(optionalUserEmail,user);
        return getCorrectBody(user, user);

    }
    public Object logInUser(User user) {
        Optional<User> optionalUserEmail = logInRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(optionalUserEmail.isPresent() ){
            User userFound = optionalUserEmail.get();
            if(userFound.getConnect()==null || !userFound.getConnect()) userFound.setConnect(true);
            else if(Boolean.TRUE.equals(userFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The user is already connected");
            }
            return getCorrectBody(user, userFound);
        }else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The credentials are incorrect");
        }
    }

    public Object logOutUser(User user) {
        Optional<User> optionalUser = logInRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(optionalUser.isPresent()){
            User userFound = logInRepository.findByEmail(user.getEmail());
            if(Boolean.FALSE.equals(userFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "User already disconnected");
            }
            userFound.setConnect(false);
            logInRepository.save(userFound);
            return new HashMap<>(Map.of("status","ok"));
        }throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "???");
    }

    private Object getCorrectBody(User user, User userFound) {
        logInRepository.save(userFound);
        Map<String, Object> filteringUser = new HashMap<>();
        filteringUser.put("id", logInRepository.findByEmail(user.getEmail()).getId());
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
