package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.EvolutionDTO;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import com.endava.pokemon_challengue.models.dto.dashboard.PokemonResponseDTO;
import com.endava.pokemon_challengue.models.dto.dashboard.ResultsDTO;
import com.endava.pokemon_challengue.models.dto.requestBody.AddPokemonRequest;
import com.endava.pokemon_challengue.models.dto.requestBody.DeletePokemonRequest;
import com.endava.pokemon_challengue.models.dto.requestBody.UpdatePokemonRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.CRUDResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.DashboardResponseDTO;
import com.endava.pokemon_challengue.models.dto.responseBody.EvolutionResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SinglePokemonDetailsResponse;
import com.endava.pokemon_challengue.services.PokemonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/pokedex")
@RestController
public class PokemonController {

    private final RestTemplate restTemplate;
    private final PokemonApiService pokemonApiService;

    @Autowired
    public PokemonController(RestTemplate restTemplate, PokemonApiService pokemonApiService) {
        this.restTemplate = restTemplate;
        this.pokemonApiService = pokemonApiService;
    }

    @PostMapping("/pokemon-trainer/{username}/pokemon")
    public CRUDResponse capturePokemon(@PathVariable(name = "username") String username,
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

    @PutMapping("/pokemon-trainer/{username}/pokemon")
    public CRUDResponse updatePokemon(@PathVariable(name = "username") String username,
                                      @RequestBody UpdatePokemonRequest updatePokemonRequest) {
        Long captureId = updatePokemonRequest.getId();
        String newNickname = updatePokemonRequest.getNickname();
        return pokemonApiService.updatePokemon(captureId, newNickname, username);
    }

    @DeleteMapping("/pokemon-trainer/{username}/pokemon")
    public CRUDResponse releasePokemon(@PathVariable(name = "username") String username,
                                       @RequestBody DeletePokemonRequest deletePokemonRequest) {

        return pokemonApiService.releasePokemon(deletePokemonRequest.getId(), username);
    }

    @GetMapping("/pokemon")
    public DashboardResponseDTO getDashboard(@RequestParam int quantity, @RequestParam int offset) {
        return DashboardResponseDTO
                .builder()
                .quantity(quantity)
                .results(getDashboardResults(quantity, offset))
                .build();
    }

    @GetMapping("/{language}/pokemon")
    public SinglePokemonDetailsResponse getPokemonDetails(@PathVariable(name = "language") String language,
                                                          @RequestParam String name) {

        PokemonDTO pokemonDTO = getPokemonDTO(name);
        PokemonSpeciesDTO pokemonSpeciesDTO = getPokemonSpeciesDTO(name);
        List<AbilityDTO> abilities = getAbilitiesDTO(pokemonDTO);

        return pokemonApiService.pokemonDetails(pokemonDTO, pokemonSpeciesDTO, abilities, language);
    }

    @GetMapping("/{language}/pokemon/evolution-chain")
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

    public List<PokemonResponseDTO> getDashboardResults(int quantity, int offset){
        String pokemonDashboardUrl = "https://pokeapi.co/api/v2/pokemon?limit="+quantity+"&offset="+offset;
        ResultsDTO resultsDTO = restTemplate.getForObject(pokemonDashboardUrl, ResultsDTO.class);
        List<PokemonResponseDTO> pokemons = new ArrayList<>();

        for(int i=0;i<resultsDTO.getResults().size();i++){
            PokemonDTO pokemonDTO = getPokemonDTO(resultsDTO.getResults().get(i).getName());
            List<String> types = new ArrayList<>();

            for(int w=0; w<pokemonDTO.getTypes().size();w++){
                types.add(pokemonDTO.getTypes().get(w).getType().getName());
            }
            pokemons.add(PokemonResponseDTO
                    .builder()
                    .id(pokemonDTO.getId())
                    .name(pokemonDTO.getName())
                    .img_path(pokemonDTO.getSprites().getOther().getDream_world().getFront_default())
                    .types(types)
                    .build());
        }
        return pokemons;
    }

}