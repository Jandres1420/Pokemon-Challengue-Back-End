package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.dto.EvolutionDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.requestBody.AddPokemonRequest;
import com.endava.pokemonChallengue.models.dto.responseBody.AddPokemonResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.EvolutionChainResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.EvolutionResponse;
import com.endava.pokemonChallengue.models.dto.responseBody.SinglePokemonDetailsResponse;
import com.endava.pokemonChallengue.services.PokemonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PokemonController {

    private final RestTemplate restTemplate;
    private final PokemonApiService pokemonApiService;

    @Autowired
    public PokemonController(RestTemplate restTemplate, PokemonApiService pokemonApiService) {
        this.restTemplate = restTemplate;
        this.pokemonApiService = pokemonApiService;
    }

    @RequestMapping(path = "/pokedex/pokemon-trainer/{username}/pokemon")
    @PostMapping()
    public AddPokemonResponse capturePokemon(@PathVariable(name = "username") String username,
                                             @RequestBody AddPokemonRequest addPokemonRequest) {

        String pokemonName = addPokemonRequest.getName();
        int pokemonId = addPokemonRequest.getId();
        String pokemonNickname = addPokemonRequest.getNickname();

        PokemonDTO pokemonDTO = getPokemonDTO(pokemonName);
        PokemonSpeciesDTO pokemonSpeciesDTO = getPokemonSpeciesDTO(pokemonName);
        List<AbilityDTO> abilities = getAbilitiesDTO(pokemonDTO);

        return  pokemonApiService.pokemonCapture(username,
                pokemonName,
                pokemonId,
                pokemonNickname,
                pokemonDTO,
                pokemonSpeciesDTO,
                abilities);
    }

    @RequestMapping(path = "/pokedex/{language}/pokemon")
    @GetMapping()
    public SinglePokemonDetailsResponse getPokemonDetails(@PathVariable(name = "language") String language,
                                                          @RequestParam String name) {

        PokemonDTO pokemonDTO = getPokemonDTO(name);
        PokemonSpeciesDTO pokemonSpeciesDTO = getPokemonSpeciesDTO(name);
        List<AbilityDTO> abilities = getAbilitiesDTO(pokemonDTO);

        return pokemonApiService.pokemonDetails(pokemonDTO, pokemonSpeciesDTO, abilities, language);
    }

    @RequestMapping(path = "/pokedex/{language}/pokemon/evolution-chain")
    @GetMapping()
    public EvolutionResponse getPokemonEvolution(@PathVariable(name = "language") String language,
                                                 @RequestParam String name) {

        String evolutionUrl = pokemonApiService.findEvolutionUrl(name);

        if(evolutionUrl.equals("")){
            PokemonSpeciesDTO pokemonSpeciesDTO = getPokemonSpeciesDTO(name);
            evolutionUrl = pokemonSpeciesDTO.getEvolution_chain().getUrl();
        }

        EvolutionDTO evolutionDTO = restTemplate.getForObject(evolutionUrl, EvolutionDTO.class);

        if(evolutionDTO.getChain().getEvolves_to().size() == 1) {
            return pokemonApiService.pokemonSequenceEvolution(evolutionDTO, language, name);
        }else{
            return pokemonApiService.pokemonBranchEvolution(evolutionDTO, language);
        }
    }

    public PokemonDTO getPokemonDTO(String pokemonName){
        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/"+pokemonName;
        return restTemplate.getForObject(urlPokemon, PokemonDTO.class);
    }

    public PokemonSpeciesDTO getPokemonSpeciesDTO(String pokemonName){
        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/"+pokemonName;
        return restTemplate.getForObject(urlSpecies, PokemonSpeciesDTO.class);
    }

    public List<AbilityDTO> getAbilitiesDTO(PokemonDTO pokemonDTO){
        int abilitiesSize = pokemonDTO.getAbilities().size();
        List<AbilityDTO> abilities = new ArrayList<>();

        for(int i=0;i<abilitiesSize;i++){
            String urlAbility = pokemonDTO.getAbilities().get(i).getAbility().getUrl();
                abilities.add(restTemplate.getForObject(urlAbility, AbilityDTO.class));
        }
        return abilities;
    }

}
