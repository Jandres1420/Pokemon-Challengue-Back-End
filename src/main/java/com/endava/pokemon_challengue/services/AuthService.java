package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.ExceptionGenerator;
import com.endava.pokemon_challengue.exceptions.ExceptionType;
import com.endava.pokemon_challengue.models.UserInfo;
import com.endava.pokemon_challengue.models.dto.responseBody.LogInResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.LogOutResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SignUpResponse;
import com.endava.pokemon_challengue.repositories.UserRepository;
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

    public SignUpResponse signUp(UserInfo userInfo) {
        Optional<UserInfo> optionalUserEmail = userRepository.findUserByEmailAndUsername(userInfo.getEmail(), userInfo.getUsername());
        signUpExceptions(optionalUserEmail, userInfo);
        if(!optionalUserEmail.isPresent()){
            userInfo.setConnect(false);
            userRepository.save(userInfo);
            return SignUpResponse.builder()
                    .id(userInfo.getUser_id())
                    .email(userInfo.getEmail())
                    .username(userInfo.getUsername())
                    .build();
        }
        return null;
    }
    public LogInResponse logInUser(UserInfo userInfo) {
        Optional<UserInfo> optionalUserEmail = userRepository.findByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());
        if(optionalUserEmail.isPresent()){
            UserInfo userInfoFound = optionalUserEmail.get();

            if(userInfoFound.getConnect()==null || !userInfoFound.getConnect()) userInfoFound.setConnect(true);
            else if(Boolean.TRUE.equals(userInfoFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The user is already connected");
            }
            userRepository.save(userInfoFound);
            return LogInResponse.builder()
                    .id(userInfoFound.getUser_id())
                    .email(userInfoFound.getEmail())
                    .username(userInfoFound.getUsername())
                    .build();
        }else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The credentials are incorrect");
        }
    }

    public LogOutResponse logOutUser(UserInfo userInfo) {
        Optional<UserInfo> optionalUser = userRepository.findByEmailAndPassword(userInfo.getEmail(), userInfo.getPassword());
        if(optionalUser.isPresent()){
            UserInfo userInfoFound = userRepository.findByEmail(userInfo.getEmail());
            if(Boolean.FALSE.equals(userInfoFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "User already disconnected");
            }
            userInfoFound.setConnect(false);
            userRepository.save(userInfoFound);
            return LogOutResponse.builder()
                    .status("ok")
                    .build();
        }throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Service unavailable");
    }

    public void signUpExceptions(Optional<UserInfo> optionalUserEmail, UserInfo userInfo) {
        if (userInfo.getEmail() == null || userInfo.getUsername() == null || userInfo.getRole()==null) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Fields email or username or role not null");
        } else if (optionalUserEmail.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_VALUE, "Email already in use or Username already in use");
        } else if (!EmailValidator.getInstance().isValid(userInfo.getEmail())) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid email");
        }
    }
}
