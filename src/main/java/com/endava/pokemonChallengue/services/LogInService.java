package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.User;
import com.endava.pokemonChallengue.models.dto.login.LogInDto;
import com.endava.pokemonChallengue.models.dto.login.LogOutDto;
import com.endava.pokemonChallengue.models.dto.login.SignInDto;
import com.endava.pokemonChallengue.repositories.LogInRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class LogInService {
    @Autowired
    private final LogInRepository logInRepository;

    @Autowired
    public LogInService(LogInRepository logInRepository){
        this.logInRepository = logInRepository;
    }

    public SignInDto signIn(User user) {
        Optional<User> optionalUserEmail = logInRepository.findUserByEmailAndUsername(user.getEmail(), user.getUsername());
        signInExceptions(optionalUserEmail,user);
        SignInDto signInDto = null;
        if(!optionalUserEmail.isPresent()){
            logInRepository.save(user);
            return SignInDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
        }
        return signInDto;
    }
    public LogInDto logInUser(User user) {
        Optional<User> optionalUserEmail = logInRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(optionalUserEmail.isPresent() ){
            User userFound = optionalUserEmail.get();
            if(userFound.getConnect()==null || !userFound.getConnect()) userFound.setConnect(true);
            else if(Boolean.TRUE.equals(userFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The user is already connected");
            }logInRepository.save(userFound);
            return LogInDto.builder()
                    .id(userFound.getId())
                    .email(userFound.getEmail())
                    .username(userFound.getUsername())
                    .build();
        }else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The credentials are incorrect");
        }
    }

    public LogOutDto logOutUser(User user) {
        Optional<User> optionalUser = logInRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(optionalUser.isPresent()){
            User userFound = logInRepository.findByEmail(user.getEmail());
            if(Boolean.FALSE.equals(userFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "User already disconnected");
            }
            userFound.setConnect(false);
            logInRepository.save(userFound);
            System.out.println("ROLE " + userFound.getRole());
            return LogOutDto.builder()
                    .status("ok")
                    .build();
        }throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Service unavailable");
    }



    public void signInExceptions(Optional<User> optionalUserEmail,User user) {
        if (user.getEmail() == null || user.getUsername() == null || user.getRole()==null) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Fields email or username or role not null");
        } else if (optionalUserEmail.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_VALUE, "Email already in use or Username already in use");
        } else if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid email");
        }
    }



}
