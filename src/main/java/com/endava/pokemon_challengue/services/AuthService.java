package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.ExceptionGenerator;
import com.endava.pokemon_challengue.exceptions.ExceptionType;
import com.endava.pokemon_challengue.models.UserProfile;
import com.endava.pokemon_challengue.models.Role;
import com.endava.pokemon_challengue.models.dto.ForgotPassword.ForgotPasswordDTO;
import com.endava.pokemon_challengue.models.dto.requestBody.LogInDto;
import com.endava.pokemon_challengue.models.dto.requestBody.SignUpDto;
import com.endava.pokemon_challengue.models.dto.responseBody.ForgotPasswordResponse;
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
        Optional<UserProfile> optionalUserEmail = userProfileRepository.findUserByEmailAndUsername(
                signUpDto.getEmail(),
                signUpDto.getUsername());
        signUpExceptions(optionalUserEmail, signUpDto);
        if(!optionalUserEmail.isPresent()){
            UserProfile userProfile = UserProfile.builder()
                    .name(signUpDto.getName())
                    .questionAnswer(signUpDto.getQuestionAnswer())
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

    public LogInResponse logInUser(@Valid @NotNull @NotEmpty LogInDto logInDto) {
        Optional<UserProfile> optionalUserEmail = userProfileRepository.findByEmailAndPassword(logInDto.getEmail(),
                logInDto.getPassword());

        if(optionalUserEmail.isPresent()){
            UserProfile userProfileFound = optionalUserEmail.get();
            if(userProfileFound.getConnect()==null || !userProfileFound.getConnect()) userProfileFound.setConnect(true);
            else if(Boolean.TRUE.equals(userProfileFound
                    .getConnect())) throw ExceptionGenerator.getException(
                            ExceptionType.INVALID_VALUE, "The user is already connected");
            userProfileRepository.save(userProfileFound);
            return LogInResponse.builder()
                    .id(userProfileFound.getUser_id())
                    .email(userProfileFound.getEmail())
                    .username(userProfileFound.getUsername())
                    .name(userProfileFound.getName())
                    .last_name(userProfileFound.getLastName())
                    .password(userProfileFound.getPassword())
                    .role(userProfileFound.getRole())
                    .build();
        }else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The credentials are incorrect");
        }
    }

    public ForgotPasswordResponse forgotPassword(@Valid @NotNull @NotEmpty ForgotPasswordDTO forgotPasswordDTO) {
        Optional<UserProfile> optionalUserEmail = userProfileRepository.findByEmail(forgotPasswordDTO.getEmail());
        if(forgotPasswordDTO.getNewPassword().equals(forgotPasswordDTO.getConfirmPassword())){
            if(optionalUserEmail.isPresent()){
                UserProfile userProfileFound = optionalUserEmail.get();
                if(userProfileFound.getQuestionAnswer().equals(forgotPasswordDTO.getQuestion_answer())) {
                    userProfileFound.setPassword(forgotPasswordDTO.getConfirmPassword());
                    userProfileRepository.save(userProfileFound);
                    return ForgotPasswordResponse.builder().message("Password updated").build();
                }throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Wrong confirmation answer");
            }else {
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Email not found");
            }
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Password mismatch");
    }


    public LogOutResponse logOutUser(LogInDto logInDto) {
        Optional<UserProfile> optionalUser = userProfileRepository.findByEmailAndPassword(logInDto.getEmail(),
                logInDto.getPassword());

        if(optionalUser.isPresent()){
            UserProfile userProfileFound = optionalUser.get();
            if(Boolean.FALSE.equals(userProfileFound.getConnect())){
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User already disconnected");
            }
            userProfileFound.setConnect(false);
            userProfileRepository.save(userProfileFound);
            return LogOutResponse.builder()
                    .status("ok")
                    .build();
        }throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Wrong credentials");
    }

    public void signUpExceptions(Optional<UserProfile> optionalUserEmail, SignUpDto userInfo) {
        if (userInfo.getEmail() == null || userInfo.getUsername() == null) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED,
                    "Fields email or username or role not null");

        } else if (optionalUserEmail.isPresent()) {
            throw ExceptionGenerator.getException(ExceptionType.DUPLICATE_VALUE, "Email/username not available");
        } else if (!EmailValidator.getInstance().isValid(userInfo.getEmail())) {
            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid email");
        }
    }
}
