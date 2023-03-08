package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.ExceptionGenerator;
import com.endava.pokemon_challengue.exceptions.ExceptionType;
import com.endava.pokemon_challengue.models.*;
import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.IndividualPokemonFromTrainerDto;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.repositories.CaptureRepository;
import com.endava.pokemon_challengue.repositories.PokemonRepository;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final UserProfileRepository userProfileRepository;
    private final CaptureRepository captureRepository;
    private final PokemonRepository pokemonRepository;

    private static  String userNotFound = "User not found";

    public SeePokemonFromTrainerDto seePokemonFromTrainer(String username,
                                                          int quantity,
                                                          int offset,
                                                          String usernameAsking,
                                                          String type,
                                                          String sortBy) {
        exceptionRole(usernameAsking);
        Optional<UserProfile> userAsking = userProfileRepository.findByUsername(usernameAsking);
        Optional<UserProfile> userInfo = userProfileRepository.findByUsername(username);

        if (userAsking.isPresent() && userInfo.isPresent()) {
            Set<UserProfile> following = userAsking.get().getFollowing();

            if (userAsking.get().getRole().equals(Role.OAK) || userAsking.get().getRole().equals(Role.ADMIN)) {
                return getPokemonFromTrainer(username,quantity,offset,type,sortBy);
            } else if (userAsking.get().getUsername().equals(userInfo.get().getUsername()) || following.contains(userInfo.get())) {
                return getPokemonFromTrainer(username,quantity,offset,type,sortBy);
            } else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "You are not following this Trainer");

        } throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "The Trainer you are looking for does not exist");
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
            for (int i = offset; i < quantity; i++) {
                if (i == captureList.size()) {
                    break;
                }

                ArrayList<String> types = new ArrayList<>(Arrays.asList(captureList
                        .get(i)
                        .getPokemon()
                        .getType()
                        .replaceAll("\\s+","")
                        .split(",")));

                pokemonsFromTrainer.add(IndividualPokemonFromTrainerDto
                        .builder()
                        .nickname(captureList.get(i).getNickname())
                        .name(captureList.get(i).getPokemon().getName())
                        .id(captureList.get(i).getPokemon().getPokemon_id())
                        .type(types)
                        .build());
            }

            return SeePokemonFromTrainerDto
                    .builder()
                    .username(username)
                    .index(offset)
                    .quantity(quantity)
                    .result(filterType(type, sortBy(sortBy, pokemonsFromTrainer)))
                    .build();
        }
        return null;
    }


    public void exceptionRole(String usernameRol){
        Optional<UserProfile> optionalUserInfo = userProfileRepository.findByUsername(usernameRol);
        if(optionalUserInfo.isPresent()){
            UserProfile userProfile = optionalUserInfo.get();
            if (!userProfile.getConnect())
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        } else{
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, userNotFound);
        }
    }

    public GeneralResponse curePokemonDoctor(Long captureId, String usernameRole) {
        exceptionRole(usernameRole);
        Optional<Capture> optionalCapture = captureRepository.findCaptureByCaptureId(captureId);
        Optional<UserProfile> userInfo = userProfileRepository.findByUsername(usernameRole);
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

    public GeneralResponse followAndUnfollowTrainer(String trainerToFollow, FollowRequest followRequest, String trainer) {
        exceptionRole(trainer);
        Optional<UserProfile>optionalUserInfo = userProfileRepository.findByUsername(trainer);
        Optional<UserProfile>optionalUserInfo2 = userProfileRepository.findByUsername(trainerToFollow);
        if(followRequest.getAction().equals("follow")){
            if (optionalUserInfo2.isPresent() && optionalUserInfo.isPresent()){
                UserProfile userProfile = userProfileRepository.findByUsername(trainer).get();
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
                UserProfile userProfile = userProfileRepository.findByUsername(trainer).get();
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

    public GeneralResponse administrateProfiles(AdminRoleChange followRequest, String admin) {
        userExist(followRequest.getUsername());
        userExist(admin);
        exceptionRole(admin);
        if(userProfileRepository.findByUsername(admin).get().getRole().equals(Role.ADMIN)){
            UserProfile userProfile = userProfileRepository.findByUsername(followRequest.getUsername()).get();
            if(followRequest.getRole().equals("admin")){
                userProfile.setRole(Role.ADMIN);
                userProfileRepository.save(userProfile);
            }else if (followRequest.getRole().equals("doctor")) {
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
                    .filter(p -> p.getType().contains(type))
                    .collect(Collectors.toList());
        }
        return pokemonsFromTrainer;
    }


}
