package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.exceptions.ExceptionGenerator;
import com.endava.pokemonChallengue.exceptions.ExceptionType;
import com.endava.pokemonChallengue.models.*;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.EvolutionDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.ability.AbilityResponseDTO;
import com.endava.pokemonChallengue.models.dto.evolution.EvolvesToDTO;
import com.endava.pokemonChallengue.models.dto.evolution.SpeciesDTO;
import com.endava.pokemonChallengue.models.dto.responseBody.CRUDResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.EvolutionChainResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.EvolutionResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.SinglePokemonDetailsResponse;
import com.endava.pokemonChallengue.models.dto.stat.StatResponseDTO;
import com.endava.pokemonChallengue.repositories.CaptureRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.UserRepository;
import com.endava.pokemonChallengue.services.methods.AbilityGetter;
import com.endava.pokemonChallengue.services.methods.DescriptionGetter;
import com.endava.pokemonChallengue.services.methods.PokemonGetter;
import com.endava.pokemonChallengue.services.methods.StatGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PokemonApiService {
    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;
    private final CaptureRepository captureRepository;

    PokemonGetter pokemonGetter = new PokemonGetter();
    DescriptionGetter descriptionGetter = new DescriptionGetter();
    AbilityGetter abilityGetter = new AbilityGetter();
    StatGetter statGetter = new StatGetter();


    public void addPokemonDB(PokemonDTO pokemonDTO,
                             PokemonSpeciesDTO pokemonSpeciesDTO,
                             List<AbilityDTO> abilitiesDTO){
        Pokemon pokemon = pokemonGetter.getPokemon(pokemonDTO, pokemonSpeciesDTO);

        Stat stat = statGetter.getStat(pokemonDTO);
        stat.setPokemon(pokemon);
        pokemon.setStat(stat);

        Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);
        description.setPokemon(pokemon);
        pokemon.setDescription(description);

        pokemon.setAbilities(new ArrayList<>());

        for (int i = 0; i < abilitiesDTO.size(); i++) {
            Ability ability = abilityGetter.getAbility(abilitiesDTO.get(i));
            pokemon.getAbilities().add(ability);
        }
        pokemonRepository.save(pokemon);
    }

    public CRUDResponse pokemonCapture(String username,
                                       String pokemonName,
                                       int pokemonId,
                                       String pokemonNickname,
                                       PokemonDTO pokemonDTO,
                                       PokemonSpeciesDTO pokemonSpeciesDTO,
                                       List<AbilityDTO> abilitiesDTO) {

        if(!pokemonRepository.findPokemonByNameOrId(pokemonId, pokemonName).isPresent()) {
            addPokemonDB(pokemonDTO, pokemonSpeciesDTO, abilitiesDTO);
        }

        if(userRepository.findByUsername(username).isPresent()){
            if(userRepository.findByUsername(username).get().getConnect().booleanValue()){
                Capture capture = Capture.builder()
                        .pokemon(pokemonRepository.findPokemonByName(pokemonName).get())
                        .user(userRepository.findByUsername(username).get())
                        .health_status(pokemonDTO.getStats().get(0).getBase_stat())
                        .nickname(pokemonNickname)
                        .build();
                captureRepository.save(capture);

                return CRUDResponse.builder()
                        .responseCode("Ok")
                        .responseMessage("Pokemon "+pokemonName+" added to "+ username)
                        .build();
            }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        }else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "This user does not exist");
    }

    public void readPokemon(){

    }

    public CRUDResponse releasePokemon(Long capture_id, String username){
        CRUDResponse crudResponse = new CRUDResponse();
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);

        if(userInfo.isPresent()){
            int user_id = userInfo.get().getUser_id();
            Optional<Capture> capture = captureRepository.findCaptureByIdAndUsername(capture_id, user_id);
            if(capture.isPresent()){
                String pokemonNickname = capture.get().getNickname();
                captureRepository.deleteById(capture_id);
                return CRUDResponse
                        .builder()
                        .responseCode("Ok")
                        .responseMessage("Pokemon "+pokemonNickname+" deleted.")
                        .build();
            }
        }
        return CRUDResponse
                .builder()
                .responseCode("Error")
                .responseMessage("That trainer does not have that pokemon")
                .build();
    }

    public CRUDResponse updatePokemon(Long capture_id, String newNickname, String username){
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);

        if(userInfo.isPresent()) {
            int user_id = userInfo.get().getUser_id();
            Optional<Capture> capture = captureRepository.findCaptureByIdAndUsername(capture_id, user_id);
            if (capture.isPresent()) {
                String oldNickname = capture.get().getNickname();
                Capture updatedCapture = capture.get();
                updatedCapture.setNickname(newNickname);
                captureRepository.save(updatedCapture);

                return CRUDResponse
                        .builder()
                        .responseCode("Ok")
                        .responseMessage("Pokemon "+oldNickname+" updated to "+newNickname)
                        .build();
            }
        }
        return CRUDResponse
                .builder()
                .responseCode("Error")
                .responseMessage("That trainer does not have that pokemon")
                .build();
    }

    public SinglePokemonDetailsResponse pokemonDetails(
                                             PokemonDTO pokemonDTO,
                                             PokemonSpeciesDTO pokemonSpeciesDTO,
                                             List<AbilityDTO> abilitiesDTO,
                                             String language) {

        Pokemon pokemon = pokemonGetter.getPokemon(pokemonDTO, pokemonSpeciesDTO);
        Stat statEntity = statGetter.getStat(pokemonDTO);

        StatResponseDTO stat = StatResponseDTO.builder()
                .attack(statEntity.getAttack())
                .speed(statEntity.getSpeed())
                .specialAttack(statEntity.getSpecialAttack())
                .specialDefense(statEntity.getSpecialDefense())
                .defense(statEntity.getDefense())
                .health(statEntity.getHealth())
                .build();

        Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);
        String descriptionText = "";

        List<String> types = new ArrayList<>(Arrays.asList(pokemon.getType().split(",")));
        types.remove(types.size()-1);

        List<AbilityResponseDTO> abilities = new ArrayList<>();

        if (language.equals("es")) {
            descriptionText = description.getD_spanish();
            for(int i=0; i<abilitiesDTO.size();i++){
                Ability ability = abilityGetter.getAbility(abilitiesDTO.get(i));
                abilities.add(AbilityResponseDTO
                        .builder().name(ability.getN_spanish()).description(ability.getD_spanish()).build());
            }
        } else if (language.equals("de")) {
            descriptionText = description.getD_german();
            for(int i=0; i<abilitiesDTO.size();i++){
                Ability ability = abilityGetter.getAbility(abilitiesDTO.get(i));
                abilities.add(AbilityResponseDTO
                        .builder().name(ability.getN_german()).description(ability.getD_german()).build());
            }
        } else if (language.equals("ja")) {
            descriptionText = description.getD_japanese();
            for(int i=0; i<abilitiesDTO.size();i++){
                Ability ability = abilityGetter.getAbility(abilitiesDTO.get(i));
                abilities.add(AbilityResponseDTO
                        .builder().name(ability.getN_japanese()).description(ability.getD_japanese()).build());
            }
        } else {
            descriptionText = description.getD_english();
            for(int i=0; i<abilitiesDTO.size();i++){
                Ability ability = abilityGetter.getAbility(abilitiesDTO.get(i));
                abilities.add(AbilityResponseDTO
                        .builder().name(ability.getN_english()).description(ability.getD_english()).build());
            }
        }

        return SinglePokemonDetailsResponse.builder()
                .name(pokemon.getName())
                .id(pokemon.getPokemon_id())
                .type(types)
                .language(language)
                .img_path(pokemon.getImg_path())
                .description(descriptionText)
                .stats(stat)
                .abilities(abilities)
                .build();
    }

    public EvolutionResponse pokemonSequenceEvolution(EvolutionDTO evolutionDTO, String language, String name){
        List<EvolutionChainResponse> sequenceEvolution = new ArrayList<>();
        EvolvesToDTO chain = evolutionDTO.getChain().getEvolves_to().get(0);
        int exit = 1;

        sequenceEvolution.add(buildSpecies(evolutionDTO.getChain().getSpecies(), language));

        while(exit>0){
            sequenceEvolution.add(buildSpecies(chain.getSpecies(), language));
            chain = chain.getEvolves_to().get(0);

            if(chain.getEvolves_to().size() == 0){
                sequenceEvolution.add(buildSpecies(chain.getSpecies(), language));
                exit--;
            }
        }

        List<EvolutionChainResponse> nextEvolution = new ArrayList<>();

        for(int i=0; i<sequenceEvolution.size()-1;i++){
            if(name.equals(sequenceEvolution.get(i).getName())){
                nextEvolution.add(sequenceEvolution.get(i+1));
            }
        }

        return EvolutionResponse
                .builder().evolution_chain(sequenceEvolution).next_evolution(nextEvolution).build();
    }

    public EvolutionResponse pokemonBranchEvolution(EvolutionDTO evolutionDTO, String language){
        List<EvolutionChainResponse> branchEvolution = new ArrayList<>();
        int evolutionSize = evolutionDTO.getChain().getEvolves_to().size();

        branchEvolution.add(buildSpecies(evolutionDTO.getChain().getSpecies(), language));
        for(int i = 0; i < evolutionSize; i++){
            SpeciesDTO species = evolutionDTO.getChain().getEvolves_to().get(i).getSpecies();
            branchEvolution.add(i+1, buildSpecies(species,language));
        }

        return EvolutionResponse
                .builder().evolution_chain(branchEvolution).next_evolution(branchEvolution).build();
    }

    public String findEvolutionUrl(String name){
        Optional<Pokemon> pokemon = pokemonRepository.findPokemonByName(name);
        if (pokemon.isPresent()){
            return pokemon.get().getEvolution_url();
        }else{
            return "";
        }
    }

    public EvolutionChainResponse buildSpecies(SpeciesDTO speciesDTO, String language){
        String name = speciesDTO.getName();

        return EvolutionChainResponse
                .builder()
                .name(name)
                .detailed_url("/pokedex/"+language+"/pokemon/"+name)
                .build();
    }


}

