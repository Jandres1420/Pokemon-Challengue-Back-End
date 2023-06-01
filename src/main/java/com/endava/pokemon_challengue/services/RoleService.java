package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.ExceptionGenerator;
import com.endava.pokemon_challengue.exceptions.ExceptionType;
import com.endava.pokemon_challengue.models.*;
import com.endava.pokemon_challengue.models.dto.generalType.PokemonTypesDTO;
import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.IndividualPokemonFromTrainerDto;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.repositories.CaptureRepository;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserProfileRepository userProfileRepository;
    private final CaptureRepository captureRepository;


    private static  String userNotFound = "User not found";

    public SeePokemonFromTrainerDto seePokemonFromTrainer(String username,
                                                          int quantity,
                                                          int offset,
                                                          String connected,
                                                          String type,
                                                          String sortBy) {
        exceptionRole(connected);
        Optional<UserProfile> userAsking = userProfileRepository.findByUsername(connected);
        Optional<UserProfile> userInfo = userProfileRepository.findByUsername(username);

        if (userAsking.isPresent() && userInfo.isPresent()) {
            Set<UserProfile> following = userAsking.get().getFollowing();

            if (userAsking.get().getRole().equals(Role.OAK) || userAsking.get().getRole().equals(Role.ADMIN)) {
                return getPokemonFromTrainer(username,quantity,offset,type,sortBy);
            } else if (userAsking.get().getUsername().equals(userInfo.get().getUsername()) || following.contains(userInfo.get())) {
                return getPokemonFromTrainer(username,quantity,offset,type,sortBy);
            } else throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE,
                    "You don't follow this pokémon trainer");

        } throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE,
                "The Trainer you are looking for does not exist");
    }

    public SeePokemonFromTrainerDto getPokemonFromTrainer(String username,
                                                          int quantity,
                                                          int offset,
                                                          String type,
                                                          String sortBy) {

        Optional<UserProfile> foundUser = userProfileRepository.findByUsername(username);
        if (foundUser.isPresent()) {
            List<Capture> captureList = foundUser.get().getCaptures();
            Collection<IndividualPokemonFromTrainerDto> pokemonsFromTrainer = new ArrayList<>();
            int end = Math.min(offset + quantity, captureList.size());
            for (int i = offset; i < end; i++) {
                Capture capture = captureList.get(i);
                ArrayList<String> types = new ArrayList<>(Arrays.asList(capture
                        .getPokemon()
                        .getType()
                        .replaceAll("\\s+","")
                        .split(",")));

                IndividualPokemonFromTrainerDto pokemon = IndividualPokemonFromTrainerDto
                        .builder()
                        .nickname(capture.getNickname())
                        .name(capture.getPokemon().getName())
                        .id(capture.getPokemon().getPokemon_id())
                        .types(types)
                        .img_path(capture.getPokemon().getImg_path())
                        .build();
                pokemonsFromTrainer.add(pokemon);
            }
            return SeePokemonFromTrainerDto
                    .builder()
                    .username(username)
                    .index(offset)
                    .quantity(quantity)
                    .results(filterType(type, sortBy(sortBy, pokemonsFromTrainer)))
                    .build();
        }
        return null;
    }


    public void exceptionRole(String connected){
        Optional<UserProfile> optionalUserInfo = userProfileRepository.findByUsername(connected);
        if(optionalUserInfo.isPresent()){
            UserProfile userProfile = optionalUserInfo.get();
            if (!userProfile.getConnect())
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        } else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, userNotFound);
        }
    }

    public GeneralResponse curePokemonDoctor(Long captureId, String connected) {
        exceptionRole(connected);
        Optional<Capture> optionalCapture = captureRepository.findCaptureByCaptureId(captureId);
        Optional<UserProfile> userInfo = userProfileRepository.findByUsername(connected);
        if(userInfo.get().getRole().equals(Role.DOCTOR) || userInfo.get().getRole().equals(Role.ADMIN)){
            if(optionalCapture.isPresent()){
                Capture capture = optionalCapture.get();
                capture.setHealth_status(capture.getPokemon().getStat().getHealth());
                captureRepository.save(capture);
                return GeneralResponse.builder()
                        .responseCode("Ok")
                        .responseMessage("You have cured the " +capture.getPokemon().getName() + " of "+ capture.getUser().getUsername())
                        .build();
            }throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Pokemon not found");}
        throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided adequate credentials to access this resource");
    }

    public GeneralResponse followAndUnfollowTrainer(String trainerToFollow, FollowRequest followRequest, String connected) {
        exceptionRole(connected);
        Optional<UserProfile>optionalUserInfo = userProfileRepository.findByUsername(connected);
        Optional<UserProfile>optionalUserInfo2 = userProfileRepository.findByUsername(trainerToFollow);
        if(followRequest.getAction().equals("follow")){
            if (optionalUserInfo2.isPresent() && optionalUserInfo.isPresent()){
                UserProfile userProfile = userProfileRepository.findByUsername(connected).get();
                UserProfile userToFollow = userProfileRepository.findByUsername(trainerToFollow).get();
                if(userProfile.equals(userToFollow)) throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User cannot follow himself");
                if(userProfile.getRole().equals(Role.TRAINER) && userToFollow.getRole().equals(Role.TRAINER)){
                    Set<UserProfile> userProfileSet = userProfile.getFollowing();
                    userProfileSet.add(userToFollow);
                    userProfile.setFollowing(userProfileSet);
                    userProfileRepository.save(userProfile);
                    return GeneralResponse.builder().responseCode("Ok").responseMessage("Following " +userToFollow.getUsername())
                            .build();
                }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User is not a trainer");
            }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, userNotFound);

        }else if (followRequest.getAction().equals("unfollow")){
            if (optionalUserInfo2.isPresent() && optionalUserInfo.isPresent()){
                UserProfile userProfile = userProfileRepository.findByUsername(connected).get();
                UserProfile userToFollow = userProfileRepository.findByUsername(trainerToFollow).get();
                if(userProfile.getRole().equals(Role.TRAINER) && userToFollow.getRole().equals(Role.TRAINER)){
                    Set<UserProfile> userProfileSet = userProfile.getFollowing();
                    if(!userProfileSet.contains(userToFollow))
                        throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE,"User is not following " +userToFollow.getUsername());
                    userProfileSet.remove(userToFollow);
                    userProfile.setFollowing(userProfileSet);
                    userProfileRepository.save(userProfile);
                    return GeneralResponse.builder().responseCode("Ok").responseMessage("Unfollowing " +userToFollow.getUsername())
                            .build();

                }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User is not a trainer");
            }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
        }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Action not found");
    }

    public GeneralResponse administrateProfiles(AdminRoleChange followRequest, String connected) {
        userExist(followRequest.getUsername());
        userExist(connected);
        exceptionRole(connected);
        if(userProfileRepository.findByUsername(connected).get().getRole().equals(Role.ADMIN)){
            UserProfile userProfile = userProfileRepository.findByUsername(followRequest.getUsername()).get();
            if(followRequest.getRole().equals("admin")){
                userProfile.setRole(Role.ADMIN);
                userProfileRepository.save(userProfile);
            } else if (followRequest.getRole().equals("doctor")) {
                userProfile.setRole(Role.DOCTOR);
                userProfileRepository.save(userProfile);
            }else if (followRequest.getRole().equals("professor")) {
                userProfile.setRole(Role.OAK);
                userProfileRepository.save(userProfile);
            }else if (followRequest.getRole().equals("trainer")) {
                userProfile.setRole(Role.TRAINER);
                userProfileRepository.save(userProfile);
            }else{
                throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided an adequate role");
            }return GeneralResponse.builder().responseCode("Ok").responseMessage("Role updated successfully.").build();
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided adequate credentials to access this resource");
    }

    public void userExist(String name){
        Optional<UserProfile> optionalUserInfo = userProfileRepository.findByUsername(name);
        if(!optionalUserInfo.isPresent()){
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User " +name+ " not found");
        }
    }

    public Collection<IndividualPokemonFromTrainerDto> sortBy(String sortBy,
                                                              Collection<IndividualPokemonFromTrainerDto> pokemonsFromTrainer) {
        switch (sortBy) {
            case "name":
                return pokemonsFromTrainer
                        .stream()
                        .sorted(Comparator.comparing(IndividualPokemonFromTrainerDto::getName))
                        .collect(Collectors.toList());
            case "id":
                return pokemonsFromTrainer
                        .stream()
                        .sorted(Comparator.comparing(IndividualPokemonFromTrainerDto::getId))
                        .collect(Collectors.toList());
            default:  return pokemonsFromTrainer;
        }
    }

    public Collection<IndividualPokemonFromTrainerDto> filterType(String type,
                                                                  Collection<IndividualPokemonFromTrainerDto> pokemonsFromTrainer){
        if(!type.equals("default value")){
            return pokemonsFromTrainer
                    .stream()
                    .filter(p -> p.getTypes().contains(type))
                    .collect(Collectors.toList());
        }
        return pokemonsFromTrainer;
    }


}
