package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.*;
import com.endava.pokemonChallengue.models.dto.responseBody.ResponseDoctorDto;
import com.endava.pokemonChallengue.models.dto.responseBody.ResultOakResponseDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
import com.endava.pokemonChallengue.repositories.CaptureRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.StatRepository;
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
    private final CaptureRepository captureRepository;
    private final PokemonRepository pokemonRepository;
    private final StatRepository statRepository;

    /*public SeePokemonOakResponseDto seeAllPokemonsProfessorOakAllParams(String trainerUsername,  int quantity,
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
                    List<String> types = new ArrayList<>(Arrays.asList(captureList.get(i)
                            .getPokemon()
                            .getType()
                            .split(",")));
                    types.remove(types.size()-1);

                    resultOakResponseDtos.add(ResultOakResponseDto.builder()
                            .name(captureList.get(i).getPokemon().getName())
                            .id(captureList.get(i).getPokemon().getPokemon_id())
                            .result(types).build());
                    seePokemonOakResponseDto.setResult(resultOakResponseDtos);
                }
                return seePokemonOakResponseDto;
            }
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User with insufficient privileges");
    }*/

    public SeePokemonOakResponseDto seeAllPokemonsProfessorOakQuantity(String trainerUsername, int quantity ,String usernameRol){
        exceptionRole(usernameRol);
        if(userRepository.findByUsername(usernameRol).get().getRole().equals(Role.OAK)){
            Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(trainerUsername);
            if(optionalUserInfo.isPresent()){
                List<Capture> captureList =optionalUserInfo.get().getCaptures();
                SeePokemonOakResponseDto seePokemonOakResponseDto = SeePokemonOakResponseDto.builder().quantity(quantity)
                        .index(0)
                        .build();
                List<ResultOakResponseDto> resultOakResponseDtos = new ArrayList<>();
                for (int i = 0 ; i<quantity;i++) {
                    List<String> types = new ArrayList<>(Arrays.asList(captureList.get(i).getPokemon().getType().split(",")));types.remove(types.size()-1);
                    resultOakResponseDtos.add(ResultOakResponseDto.builder()
                            .name(captureList.get(i).getPokemon().getName())
                            .id(captureList.get(i).getPokemon().getPokemon_id())
                            .result(types).build());
                    seePokemonOakResponseDto.setResult(resultOakResponseDtos);
                }
                return seePokemonOakResponseDto;
            }throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User with insufficient privileges");
    }

    public void exceptionRole(String usernameRol){
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(usernameRol);
        if(optionalUserInfo.isPresent()){
            if (optionalUserInfo.get().getConnect().equals(Boolean.FALSE))
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        }ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
    }

    public ResponseDoctorDto curePokemonDoctor(String trainerUsername, int pokemonId, String usernameRole, Cure cure) {
        exceptionRole(usernameRole);
        if(userRepository.findByUsername(usernameRole).get().getRole().equals(Role.DOCTOR)){
            Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(trainerUsername);
            System.out.println("ES UN DOCTOR " + usernameRole);
            if(optionalUserInfo.isPresent() && (cure.getAction().equals("cure"))){
                    Optional<Pokemon> pokemon = pokemonRepository.findPokemonById(pokemonId);
                    if(pokemon.isPresent()){
                        Optional<Capture> optionalCapture = captureRepository.findCaptureByPokemon(pokemon.get());
                        Optional<Stat> optionalStat = statRepository.findByHealth(pokemon.get());
                        if(optionalCapture.isPresent() && (optionalStat.isPresent())){
                                Capture capture = optionalCapture.get();
                                capture.setHealth_status(optionalStat.get().getHealth());
                                captureRepository.save(capture);
                                return ResponseDoctorDto.builder()
                                        .responseCode("Ok")
                                        .responseMessage("Yo have cured the " +pokemon.get().getName() + " of "+trainerUsername)
                                        .build();
                        }
                    }
            } throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User with insufficient privileges");
    }

    public void reducePokemonHealth(String trainerUsername, int pokemonId,String usernameRole){
        exceptionRole(usernameRole);
        if(userRepository.findByUsername(usernameRole).get().getRole().equals(Role.ADMIN)){
            Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(trainerUsername);
            if(optionalUserInfo.isPresent()){
                UserInfo userInfo = optionalUserInfo.get();
                Optional<Pokemon> pokemon = pokemonRepository.findPokemonById(pokemonId);
                if(pokemon.isPresent()){
                    Optional<Capture> optionalCapture = captureRepository.findCaptureByPokemon(pokemon.get());
                    if(optionalCapture.isPresent()){
                        Capture capture = optionalCapture.get();
                        capture.setHealth_status(0);
                        captureRepository.save(capture);
                    }
                }
            } throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User with insufficient privileges");
    }
}
