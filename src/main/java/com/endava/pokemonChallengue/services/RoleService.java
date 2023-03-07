package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.*;
import com.endava.pokemonChallengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemonChallengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemonChallengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.ResultOakResponseDto;
import com.endava.pokemonChallengue.models.dto.responseBody.SeePokemonOakResponseDto;
import com.endava.pokemonChallengue.repositories.CaptureRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.StatRepository;
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
    private final StatRepository statRepository;

    public SeePokemonOakResponseDto seeAllPokemonsProfessorOakAllParams(String trainerUsername,  int quantity,
                                                                        int offset,  String usernameRol,String filter, String sort){
        exceptionRole(usernameRol);
        if(offset<=0) offset=1;
        else if (offset>=quantity) offset = quantity;
        else if(offset+1==quantity) offset--;
        else if(offset>=1) offset++;

        if(userRepository.findByUsername(usernameRol).get().getRole().equals(Role.OAK)){
            Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(trainerUsername);
            if(optionalUserInfo.isPresent()){
                List<Capture> captureList =optionalUserInfo.get().getCaptures();
                SeePokemonOakResponseDto seePokemonOakResponseDto = SeePokemonOakResponseDto.builder().quantity(quantity)
                        .index(offset)
                        .build();
                List<ResultOakResponseDto> resultOakResponseDtos = new ArrayList<>();
                if(offset>=captureList.size()){
                }else{
                    for (int i = offset-1 ; i<=quantity;i++) {
                        if(i==captureList.size())break;
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
                    seePokemonOakResponseDto = orderAlphabetic(sort,seePokemonOakResponseDto);
                }
                return seePokemonOakResponseDto;
            }
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "User with insufficient privileges");
    }


    public void exceptionRole(String usernameRol){
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(usernameRol);
        if(optionalUserInfo.isPresent()){
            UserInfo userInfo = optionalUserInfo.get();
            if (!userInfo.getConnect())
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

    public GeneralResponse curePokemonDoctor(Long captureId, String usernameRole) {
        exceptionRole(usernameRole);
        Optional<Capture> optionalCapture = captureRepository.findCaptureByCaptureId(captureId);
        if(userRepository.findByUsername(usernameRole).get().getRole().equals(Role.DOCTOR)){
            if(optionalCapture.isPresent()){
                Capture capture = optionalCapture.get();
                capture.setHealth_status(capture.getPokemon().getStat().getHealth());
                captureRepository.save(capture);
                return GeneralResponse.builder()
                        .responseCode("Ok")
                        .responseMessage("Yo have cured the " +capture.getPokemon().getName() + " of "+ capture.getUser().getUsername())
                        .build();
            }throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "Pokemon not found");}
        throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided adequate credentials to access this resource");
    }

    public SeePokemonOakResponseDto orderAlphabetic(String order,SeePokemonOakResponseDto seePokemonOakResponseDto){
        if(order.equals("alphabetical")){
            Collections.sort(seePokemonOakResponseDto.getResult(), Comparator.comparing(ResultOakResponseDto::getName));
            return seePokemonOakResponseDto;
        }else return seePokemonOakResponseDto;
    }

    public GeneralResponse followAndUnfollowTrainer(String trainerToFollow, FollowRequest followRequest, String trainer) {
        exceptionRole(trainer);
        Optional<UserInfo>optionalUserInfo = userRepository.findByUsername(trainer);
        Optional<UserInfo>optionalUserInfo2 = userRepository.findByUsername(trainerToFollow);
        if(followRequest.getAction().equals("follow")){
            if (optionalUserInfo2.isPresent() && optionalUserInfo.isPresent()){
                UserInfo userInfo = userRepository.findByUsername(trainer).get();
                UserInfo userToFollow = userRepository.findByUsername(trainerToFollow).get();
                if(userInfo.getRole().equals(Role.TRAINER) && userToFollow.getRole().equals(Role.TRAINER)){
                    Set<UserInfo> userInfoSet = userInfo.getFollowing();
                    userInfoSet.add(userToFollow);
                    userInfo.setFollowing(userInfoSet);
                    userRepository.save(userInfo);
                    return GeneralResponse.builder().responseCode("Ok").responseMessage("Following " +userToFollow.getUsername())
                            .build();
                }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User is not a trainer");
            }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User not found");
        }else if (followRequest.getAction().equals("unfollow")){
            if (optionalUserInfo2.isPresent() && optionalUserInfo.isPresent()){
                UserInfo userInfo = userRepository.findByUsername(trainer).get();
                UserInfo userToFollow = userRepository.findByUsername(trainerToFollow).get();
                if(userInfo.getRole().equals(Role.TRAINER) && userToFollow.getRole().equals(Role.TRAINER)){
                    Set<UserInfo> userInfoSet = userInfo.getFollowing();
                    if(!userInfoSet.contains(userToFollow))
                        throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE,"User is not following " +userToFollow.getName());
                    userInfoSet.remove(userToFollow);
                    userInfo.setFollowing(userInfoSet);
                    userRepository.save(userInfo);
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
        if(userRepository.findByUsername(admin).get().getRole().equals(Role.ADMIN)){
            UserInfo userInfo = userRepository.findByUsername(followRequest.getUsername()).get();
            if(followRequest.getRole().equals("admin")){
                userInfo.setRole(Role.ADMIN);
                userRepository.save(userInfo);
            } else if (followRequest.getRole().equals("doctor")) {
                userInfo.setRole(Role.DOCTOR);
                userRepository.save(userInfo);
            }else if (followRequest.getRole().equals("professor")) {
                userInfo.setRole(Role.OAK);
                userRepository.save(userInfo);
            }else if (followRequest.getRole().equals("trainer")) {
                userInfo.setRole(Role.TRAINER);
                userRepository.save(userInfo);
            }else{
                throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided an adequate role");
            }return GeneralResponse.builder().responseCode("Ok").responseMessage("Role updated successfully.").build();
        }throw ExceptionGenerator.getException(ExceptionType.INVALID_ROLE, "You have not provided adequate credentials to access this resource");
    }

    public void userExist(String name){
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(name);
        if(!optionalUserInfo.isPresent()){
            throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User " +name+ " not found");
        }
    }

    public void userConnected(String name){
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(name);
        if(optionalUserInfo.isPresent()){
            UserInfo userInfo = optionalUserInfo.get();
            if(!userInfo.getConnect()) {
                throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User " + name + " not connected");
            }
        }
    }
}
