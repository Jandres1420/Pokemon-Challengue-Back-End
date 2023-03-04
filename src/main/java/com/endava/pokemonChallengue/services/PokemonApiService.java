package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.stat.StatsDTO;
import com.endava.pokemonChallengue.repositories.DescriptionRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.StatRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonApiService {
    private final PokemonRepository  pokemonRepository;
    private final StatRepository statRepository;
    private final DescriptionRepository descriptionRepository;

    public void pokemonService(PokemonDTO pokemonDTO, PokemonSpeciesDTO pokemonSpeciesDTO){

        //todo CHEQUEAR SI EL POKEMON EXISTE

        Pokemon pokemon = getPokemon(pokemonDTO);
        Stat stat = getStat(pokemonDTO);

        stat.setPokemon(pokemon);
        pokemon.setStat(stat);

        pokemonRepository.save(pokemon);
        //statRepository.save(stat);

//        //Create Description
//
//
//
//        return pokemon;
    }

    public Pokemon getPokemon(PokemonDTO pokemonDTO){

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

        return pokemon;
    }

    public Stat getStat(PokemonDTO pokemonDTO){
        List<StatsDTO> stats = pokemonDTO.getStats();

       return Stat.builder()
                .health(stats.get(0).getBase_stat())
                .attack(stats.get(1).getBase_stat())
                .defense(stats.get(2).getBase_stat())
                .specialAttack(stats.get(3).getBase_stat())
                .specialDefense(stats.get(4).getBase_stat())
                .speed(stats.get(5).getBase_stat())
                .build();
    }

    public Description getDescription(PokemonSpeciesDTO pokemonSpeciesDTO){
        int counter = 0;
        String englishDes = "";
        String spanishDes = "";
        String japaneseDes = "";
        String frenchDes = "";

        while(englishDes.isEmpty() || spanishDes.isEmpty()  || japaneseDes.isEmpty() || frenchDes.isEmpty()){
            String language = pokemonSpeciesDTO.getFlavor_text_entries().get(counter).getLanguage().getName();
            String description = pokemonSpeciesDTO.getFlavor_text_entries().get(counter).getFlavor_text();
            if(language.equals("en") && englishDes.isEmpty()){
                englishDes = description;
            } else if (language.equals("es") && spanishDes.isEmpty()) {
                spanishDes = description;
            } else if (language.equals("ja") && japaneseDes.isEmpty()) {
                japaneseDes = description;
            } else if (language.equals("fr") && frenchDes.isEmpty()){
                frenchDes = description;
            }
            counter++;
        }

        return null;

    }

    public void noLanguage(String englishDes,String spanishDes, String japaneseDes, String frenchDes ){
        if(spanishDes.isEmpty())spanishDes=englishDes;
        if(japaneseDes.isEmpty())japaneseDes=englishDes;
        if(frenchDes.isEmpty())frenchDes=englishDes;
        }

}
