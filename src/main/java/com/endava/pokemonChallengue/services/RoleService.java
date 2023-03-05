package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.Capture;
import com.endava.pokemonChallengue.models.Role;
import com.endava.pokemonChallengue.models.UserInfo;
import com.endava.pokemonChallengue.models.dto.responseBody.ResultOakResponseDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
import com.endava.pokemonChallengue.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserRepository userRepository;
    public SeePokemonOakResponseDto seeAllPokemonsProfessorOak(String trainerUsername,  int quantity,
                                                               int offset,  String usernameRol){
        exceptionRole(usernameRol);
        if(userRepository.findByUsername(usernameRol).get().getRole().equals(Role.OAK)){
            Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(trainerUsername);
            if(optionalUserInfo.isPresent()){
                List<Capture> captureList =optionalUserInfo.get().getCaptures();
                SeePokemonOakResponseDto seePokemonOakResponseDto = SeePokemonOakResponseDto.builder().quantity(quantity)
                        .index(offset)
                        .build();
                List<ResultOakResponseDto> resultOakResponseDtos = new ArrayList<>();
                for (int i = offset-1 ; i<captureList.size();i++) {
                    List<String> types = new ArrayList<>(Arrays.asList(captureList.get(i).getPokemon().getType().split(",")));types.remove(types.size()-1);
                    resultOakResponseDtos.add(ResultOakResponseDto.builder()
                            .name(captureList.get(i).getPokemon().getName())
                            .id(captureList.get(i).getPokemon().getPokemon_id())
                            .result(types).build());
                    seePokemonOakResponseDto.setResult(resultOakResponseDtos);
                }
                return seePokemonOakResponseDto;
            }
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User with insufficient privileges");
    }

    public void exceptionRole(String usernameRol){
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(usernameRol);
        if(optionalUserInfo.isPresent()){
            if (optionalUserInfo.get().getConnect().equals(Boolean.FALSE))
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        }ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
    }
}
