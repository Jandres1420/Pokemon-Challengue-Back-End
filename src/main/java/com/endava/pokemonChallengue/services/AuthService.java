package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.UserInfo;
import com.endava.pokemonChallengue.models.dto.login.LogInDto;
import com.endava.pokemonChallengue.models.dto.login.LogOutDto;
import com.endava.pokemonChallengue.models.dto.login.SignInDto;
import com.endava.pokemonChallengue.repositories.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public SignInDto signIn(UserInfo userInfo) {
        Optional<UserInfo> optionalUserEmail = userRepository.findUserByEmailAndUsername(userInfo.getEmail(), userInfo.getUsername());
        signInExceptions(optionalUserEmail, userInfo);
        SignInDto signInDto = null;
        if(!optionalUserEmail.isPresent()){
            userRepository.save(userInfo);
            return SignInDto.builder()
                    .id(userInfo.getUser_id())
                    .email(userInfo.getEmail())
                    .username(userInfo.getUsername())
                    .build();
        }
        return signInDto;
    }
    public LogInDto logInUser(UserInfo userInfo) {
        Optional<UserInfo> optionalUserEmail = userRepository.findByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());
        if(optionalUserEmail.isPresent()){
            UserInfo userInfoFound = optionalUserEmail.get();

            if(userInfoFound.getConnect()==null || !userInfoFound.getConnect()) userInfoFound.setConnect(true);
            else if(Boolean.TRUE.equals(userInfoFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The user is already connected");
            }
            userRepository.save(userInfoFound);
            return LogInDto.builder()
                    .id(userInfoFound.getUser_id())
                    .email(userInfoFound.getEmail())
                    .username(userInfoFound.getUsername())
                    .build();
        }else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The credentials are incorrect");
        }
    }

    public LogOutDto logOutUser(UserInfo userInfo) {
        Optional<UserInfo> optionalUser = userRepository.findByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());
        if(optionalUser.isPresent()){
            UserInfo userInfoFound = userRepository.findByEmail(userInfo.getEmail());
            if(Boolean.FALSE.equals(userInfoFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "User already disconnected");
            }
            userInfoFound.setConnect(false);
            userRepository.save(userInfoFound);
            System.out.println("ROLE " + userInfoFound.getRole());
            return LogOutDto.builder()
                    .status("ok")
                    .build();
        }throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Service unavailable");
    }

    public void signInExceptions(Optional<UserInfo> optionalUserEmail, UserInfo userInfo) {
        if (userInfo.getEmail() == null || userInfo.getUsername() == null || userInfo.getRole()==null) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Fields email or username or role not null");
        } else if (optionalUserEmail.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_VALUE, "Email already in use or Username already in use");
        } else if (!EmailValidator.getInstance().isValid(userInfo.getEmail())) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid email");
        }
    }
}
