package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.exceptions.ExceptionGenerator;
import com.endava.pokemon_challengue.exceptions.ExceptionType;
import com.endava.pokemon_challengue.models.*;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import com.endava.pokemon_challengue.models.dto.EvolutionDTO;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.models.dto.ability.AbilityResponseDTO;
import com.endava.pokemon_challengue.models.dto.evolution.EvolvesToDTO;
import com.endava.pokemon_challengue.models.dto.evolution.SpeciesDTO;
import com.endava.pokemon_challengue.models.dto.responseBody.CRUDResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.EvolutionChainResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.EvolutionResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SinglePokemonDetailsResponse;
import com.endava.pokemon_challengue.models.dto.stat.StatResponseDTO;
import com.endava.pokemon_challengue.repositories.CaptureRepository;
import com.endava.pokemon_challengue.repositories.PokemonRepository;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import com.endava.pokemon_challengue.services.methods.AbilityGetter;
import com.endava.pokemon_challengue.services.methods.DescriptionGetter;
import com.endava.pokemon_challengue.services.methods.PokemonGetter;
import com.endava.pokemon_challengue.services.methods.StatGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PokemonApiService {
    private final PokemonRepository pokemonRepository;
    private final UserProfileRepository userProfileRepository;
    private final CaptureRepository captureRepository;

    private final PokemonGetter pokemonGetter;
    private final DescriptionGetter descriptionGetter;
    private final AbilityGetter abilityGetter;
    private final StatGetter statGetter;


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
                                       String pokemonNickname,
                                       PokemonDTO pokemonDTO,
                                       PokemonSpeciesDTO pokemonSpeciesDTO,
                                       List<AbilityDTO> abilitiesDTO) {

        if(!pokemonRepository.findPokemonByName(pokemonName).isPresent()) {
            addPokemonDB(pokemonDTO, pokemonSpeciesDTO, abilitiesDTO);
        }

        Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
        if(userProfile.isPresent()){
            if(userProfileRepository.findByUsername(username).get().getConnect().booleanValue()){
                Capture capture = Capture.builder()
                        .pokemon(pokemonRepository.findPokemonByName(pokemonName).get())
                        .user(userProfileRepository.findByUsername(username).get())
                        .health_status(pokemonDTO.getStats().get(0).getBase_stat())
                        .nickname(pokemonNickname)
                        .build();
                captureRepository.save(capture);
                return CRUDResponse.builder()
                        .responseCode("Ok")
                        .responseMessage("Pokemon "+pokemonName+" added to "+ username)
                        .build();
            } else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "User disconnected");
        } else throw ExceptionGenerator.getException(ExceptionType.INVALID_VALUE, "This user does not exist");
    }

    public CRUDResponse releasePokemon(Long captureId, String username){
        Optional<UserProfile> userInfo = userProfileRepository.findByUsername(username);

        if(userInfo.isPresent()){
            int userId = userInfo.get().getUser_id();
            Optional<Capture> capture = captureRepository.findCaptureByCaptureIdAndUserId(captureId, userId);
            if(capture.isPresent()){
                String pokemonNickname = capture.get().getNickname();
                captureRepository.deleteById(captureId);
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

    public CRUDResponse updatePokemon(Long captureId, String newNickname, String username){
        Optional<UserProfile> userInfo = userProfileRepository.findByUsername(username);

        if(userInfo.isPresent()) {
            int userId = userInfo.get().getUser_id();
            Optional<Capture> capture = captureRepository.findCaptureByCaptureIdAndUserId(captureId, userId);
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
                .types(types)
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
            if(chain.getEvolves_to().size() == 0){
                sequenceEvolution.add(buildSpecies(chain.getSpecies(), language));
                exit--;
                break;
            }
            sequenceEvolution.add(buildSpecies(chain.getSpecies(), language));
            chain = chain.getEvolves_to().get(0);
        }

        List<EvolutionChainResponse> nextEvolution = new ArrayList<>();
        boolean found = false;

        for(int i=0; i<sequenceEvolution.size()-1;i++){
            if(name.equals(sequenceEvolution.get(i).getName())){
                found = true;
            }
            if(found){
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

        List<EvolutionChainResponse> nextEvolution = new ArrayList<>(branchEvolution);

        return EvolutionResponse
                .builder().evolution_chain(branchEvolution).next_evolution(nextEvolution).build();
    }

    public EvolutionResponse pokemonNoEvolution(EvolutionDTO evolutionDTO, String language, PokemonDTO pokemonDTO){
        List<EvolutionChainResponse> noEvolution = new ArrayList<>();
        noEvolution.add(buildSpecies(evolutionDTO.getChain().getSpecies(), language));
        return EvolutionResponse
                .builder().evolution_chain(noEvolution).next_evolution(new ArrayList<>()).build();
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

