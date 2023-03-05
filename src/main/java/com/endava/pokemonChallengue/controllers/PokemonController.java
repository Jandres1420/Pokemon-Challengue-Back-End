package com.endava.pokemonChallengue.controllers;

import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.requestBody.CaptureForm;
import com.endava.pokemonChallengue.models.dto.responseBody.AddPokemonForm;
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
    public AddPokemonForm capturePokemon(@PathVariable(name = "username") String username,
                                         @RequestBody CaptureForm captureForm) {

        String pokemonName = captureForm.getName();
        int pokemonId = captureForm.getId();
        String pokemonNickname = captureForm.getNickname();

        String urlPokemon = "https://pokeapi.co/api/v2/pokemon/"+pokemonName;
        PokemonDTO pokemonDTO = restTemplate.getForObject(urlPokemon, PokemonDTO.class);

        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/"+pokemonName;
        PokemonSpeciesDTO pokemonSpeciesDTO = restTemplate.getForObject(urlSpecies, PokemonSpeciesDTO.class);

        int abilitiesSize = pokemonDTO.getAbilities().size();
        List<AbilityDTO> abilities = new ArrayList<>();

        for(int i=0;i<abilitiesSize;i++){
            String urlAbility = pokemonDTO.getAbilities().get(i).getAbility().getUrl();
            abilities.add(restTemplate.getForObject(urlAbility, AbilityDTO.class));
        }

        return  pokemonApiService.pokemonCapture(username,
                pokemonName,
                pokemonId,
                pokemonNickname,
                pokemonDTO,
                pokemonSpeciesDTO,
                abilities);
    }

}
