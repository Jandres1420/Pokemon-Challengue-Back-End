package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.*;
import com.endava.pokemonChallengue.models.dto.responseBody.ResponseDoctorDto;
import com.endava.pokemonChallengue.models.dto.responseBody.ResultSeePokemonFromTrainerDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemonChallengue.repositories.CaptureRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserRepository userRepository;
    private final CaptureRepository captureRepository;
    private final PokemonRepository pokemonRepository;


    public SeePokemonFromTrainerDto seePokemonFromTrainer(String username,
                                                          int quantity,
                                                          int offset,
                                                          String usernameAsking,
                                                          String filter,
                                                          String sort) {
        exceptionRole(usernameAsking);
        Optional<UserInfo> userAsking = userRepository.findByUsername(usernameAsking);
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);


        if (userAsking.isPresent() && userInfo.isPresent()) {
            if (userAsking.get().getRole().equals(Role.OAK)) {
                return getPokemonFromTrainer(username,quantity,offset,filter,sort);
            } else if (userAsking.get().getUsername().equals(userInfo.get().getUsername())) {
                return getPokemonFromTrainer(username,quantity,offset,filter,sort);
            } else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "You are not following this Trainer");

        } throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The Trainer you are looking for does not exist");
    }

    public SeePokemonFromTrainerDto getPokemonFromTrainer(String username,
                                      int quantity,
                                      int offset,
                                      String filter,
                                      String sort){

        Optional<UserInfo> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            List<Capture> captureList = foundUser.get().getCaptures();
            List<ResultSeePokemonFromTrainerDto> resultSeePokemonFromTrainer = new ArrayList<>();
            for (int i = offset; i < quantity; i++) {
                if (i == captureList.size()) {
                    break;
                }
                ArrayList<String> types = new ArrayList<>(Arrays.asList(captureList
                        .get(i)
                        .getPokemon()
                        .getType()
                        .split(",")));

                types.remove(types.size() - 1);

                resultSeePokemonFromTrainer.add(ResultSeePokemonFromTrainerDto
                        .builder()
                        .name(captureList.get(i).getPokemon().getName())
                        .id(captureList.get(i).getPokemon().getPokemon_id())
                        .type(types)
                        .build());
            }

            return orderAlphabetic(sort, SeePokemonFromTrainerDto
                    .builder()
                    .index(offset)
                    .quantity(quantity)
                    .result(resultSeePokemonFromTrainer)
                    .build());
        }
        return null;
    }


    public void exceptionRole(String usernameRol){
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(usernameRol);
        if(optionalUserInfo.isPresent()){
            UserInfo userInfo = optionalUserInfo.get();
            if (!userInfo.getConnect()|| userInfo.getConnect() == null)
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        }ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
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
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided adequate credentials to access this resource");
    }

    public ResponseDoctorDto curePokemonDoctor(Long captureId, String usernameRole) {
        exceptionRole(usernameRole);
        System.out.println("ENTRO");

        Optional<Capture> optionalCapture = captureRepository.findCaptureByCaptureId(captureId);
        if(userRepository.findByUsername(usernameRole).get().getRole().equals(Role.DOCTOR)){
            if(optionalCapture.isPresent()){
                Capture capture = optionalCapture.get();
                capture.setHealth_status(capture.getPokemon().getStat().getHealth());
                captureRepository.save(capture);
                return ResponseDoctorDto.builder()
                        .responseCode("Ok")
                        .responseMessage("Yo have cured the " +capture.getPokemon().getName() + " of "+ capture.getUser().getUsername())
                        .build();
            }throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Pokemon not found");}
        throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided adequate credentials to access this resource");
    }

    public SeePokemonFromTrainerDto orderAlphabetic(String sortMethod, SeePokemonFromTrainerDto seePokemonFromTrainerDto){
        if(sortMethod.equals("alphabetical")){
            Collections.sort(seePokemonFromTrainerDto.getResult(), Comparator.comparing(ResultSeePokemonFromTrainerDto::getName));
        }
        return seePokemonFromTrainerDto;
    }
}
