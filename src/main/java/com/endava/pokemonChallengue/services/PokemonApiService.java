package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.StatRepository;
import org.springframework.stereotype.Service;

@Service
public class PokemonApiService {
    private final PokemonRepository  pokemonRepository;
    private final StatRepository statRepository;



    public PokemonApiService(PokemonRepository pokemonRepository, StatRepository statRepository){
        this.pokemonRepository = pokemonRepository;
        this.statRepository = statRepository;
    }

    public Object createPokemon(PokemonDTO pokemonDTO, PokemonSpeciesDTO pokemonSpeciesDTO){

        String types = "";
        for(int i=0;i<pokemonDTO.getTypes().size();i++){
            types += pokemonDTO.getTypes().get(i).getType().getName()+", ";
        }
        pokemonDTO.setTypeString(types);

        Pokemon pokemon = new Pokemon();
        pokemon.setPokemon_id(pokemonDTO.getId());
        pokemon.setName(pokemonDTO.getName());
        pokemon.setType(pokemonDTO.getTypeString());
        pokemon.setImg_path(pokemonDTO
                .getSprites()
                .getOther()
                .getDream_world()
                .getFront_default());

        pokemonRepository.save(pokemon);

        //Create Stats
        Stat stat = new Stat(
                pokemonDTO.getStats().get(0).getBase_stat(),
                pokemonDTO.getStats().get(1).getBase_stat(),
                pokemonDTO.getStats().get(2).getBase_stat(),
                pokemonDTO.getStats().get(3).getBase_stat(),
                pokemonDTO.getStats().get(4).getBase_stat(),
                pokemonDTO.getStats().get(5).getBase_stat(),
                pokemon);

        statRepository.save(stat);

        return pokemon;
    }



}
