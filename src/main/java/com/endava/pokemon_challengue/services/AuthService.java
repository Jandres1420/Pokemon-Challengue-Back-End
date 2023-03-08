package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.ExceptionGenerator;
import com.endava.pokemon_challengue.exceptions.ExceptionType;
import com.endava.pokemon_challengue.models.UserProfile;
import com.endava.pokemon_challengue.models.Role;
import com.endava.pokemon_challengue.models.dto.requestBody.SignUpDto;
import com.endava.pokemon_challengue.models.dto.responseBody.LogInResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.LogOutResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SignUpResponse;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public AuthService(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    public SignUpResponse signUp(@Valid @NotNull @NotEmpty SignUpDto signUpDto) {
        Optional<UserProfile> optionalUserEmail = userProfileRepository.findUserByEmailAndUsername(signUpDto.getEmail(), signUpDto.getUsername());
        signUpExceptions(optionalUserEmail, signUpDto);
        if(!optionalUserEmail.isPresent()){
            UserProfile userProfile = UserProfile.builder()
                    .name(signUpDto.getName())
                    .lastName(signUpDto.getLastname())
                    .role(Role.TRAINER)
                    .connect(false)
                    .email(signUpDto.getEmail())
                    .username(signUpDto.getUsername())
                    .password(signUpDto.getPassword())
                    .build();
            userProfileRepository.save(userProfile);

            return SignUpResponse.builder()
                    .id(userProfile.getUser_id())
                    .email(signUpDto.getEmail())
                    .username(signUpDto.getUsername())
                    .build();
        }
        return null;
    }
    public LogInResponse logInUser(UserProfile userProfile) {
        Optional<UserProfile> optionalUserEmail = userProfileRepository.findByEmailAndPassword(userProfile.getEmail(), userProfile.getPassword());
        if(optionalUserEmail.isPresent()){
            UserProfile userProfileFound = optionalUserEmail.get();

            if(userProfileFound.getConnect()==null || !userProfileFound.getConnect()) userProfileFound.setConnect(true);
            else if(Boolean.TRUE.equals(userProfileFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The user is already connected");
            }
            userProfileRepository.save(userProfileFound);
            return LogInResponse.builder()
                    .id(userProfileFound.getUser_id())
                    .email(userProfileFound.getEmail())
                    .username(userProfileFound.getUsername())
                    .build();
        }else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The credentials are incorrect");
        }
    }

    public LogOutResponse logOutUser(UserProfile userProfile) {
        Optional<UserProfile> optionalUser = userProfileRepository.findByEmailAndPassword(userProfile.getEmail(), userProfile.getPassword());
        if(optionalUser.isPresent()){
            UserProfile userProfileFound = userProfileRepository.findByEmail(userProfile.getEmail());
            if(Boolean.FALSE.equals(userProfileFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "User already disconnected");
            }
            userProfileFound.setConnect(false);
            userProfileRepository.save(userProfileFound);
            return LogOutResponse.builder()
                    .status("ok")
                    .build();
        }throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Service unavailable");
    }

    public void signUpExceptions(Optional<UserProfile> optionalUserEmail, SignUpDto userInfo) {
        if (userInfo.getEmail() == null || userInfo.getUsername() == null) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Fields email or username or role not null");
        } else if (optionalUserEmail.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_VALUE, "Email already in use or Username already in use");
        } else if (!EmailValidator.getInstance().isValid(userInfo.getEmail())) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid email");
        }
    }
}
