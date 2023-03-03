package com.endava.pokemonChallengue.api;

import com.endava.pokemonChallengue.services.PokemonApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping(path="/pokedex")
public class PokemonApiController {

    private final PokemonApiService pokemonApiService;

    public PokemonApiController(PokemonApiService pokemonApiService) {
        this.pokemonApiService = pokemonApiService;
    }

    /*
    @GetMapping(path = "/pokemon")
    public ResponseEntity<Object> getDashboard(@RequestParam Integer quantity, @RequestParam Integer offset ){
        return new ResponseEntity<>(pokemonApiService.getDashboard(quantity, offset), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "{language}/pokemon")
    @GetMapping()
    public ResponseEntity<Object> getPokemonDetails(@PathVariable(name = "language") String language,
                                                    @RequestParam String name) throws MalformedURLException {
        return new ResponseEntity<>(pokemonApiService.getPokemonDetails(name, language), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/pokemon/evolution-chain")
    public ResponseEntity<Object> getEvolution(@RequestParam String name){
        System.out.println(pokemonApiService.getEvolution(name));
        return new ResponseEntity<>(pokemonApiService.getEvolution(name), HttpStatus.ACCEPTED);
    }*/


    /*
    public Object getDashboard(Integer quantity, Integer offset){
        ApiDashboard apiDashboard = new ApiDashboard();
        return apiDashboard.showDashBoard(quantity, offset);
    }

    public Object getPokemonDetails(String value, String language) throws MalformedURLException {
        ApiPokemonDetails apiPokemonDetails = new ApiPokemonDetails();
        return apiPokemonDetails.getPokemonDetails(value, language);
    }

    public Object getEvolution(String value){
        ApiEvolution apiEvolution = new ApiEvolution();
        return apiEvolution.findEvolution(value);
    }*/
}
