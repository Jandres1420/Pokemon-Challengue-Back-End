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

    public void pokemonService(PokemonDTO pokemonDTO, PokemonSpeciesDTO pokemonSpeciesDTO){

        //todo CHEQUEAR SI EL POKEMON EXISTE

        Pokemon pokemon = getPokemon(pokemonDTO);

        Stat stat = getStat(pokemonDTO);
        stat.setPokemon(pokemon);
        pokemon.setStat(stat);

        Description description = getDescription(pokemonSpeciesDTO);
        description.setPokemon(pokemon);
        pokemon.setDescription(description);

        pokemonRepository.save(pokemon);
    }

    public Pokemon getPokemon(PokemonDTO pokemonDTO){
        String types = "";
        for(int i=0;i<pokemonDTO.getTypes().size();i++){
            types += pokemonDTO.getTypes().get(i).getType().getName()+", ";
        }
        pokemonDTO.setTypeString(types);

        String imagePath = pokemonDTO
                .getSprites()
                .getOther()
                .getDream_world()
                .getFront_default();

        return Pokemon.builder()
                .pokemon_id(pokemonDTO.getId())
                .name(pokemonDTO.getName())
                .type(types)
                .img_path(imagePath)
                .build();
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
        int size = pokemonSpeciesDTO.getFlavor_text_entries().size();

        String englishDes = "";
        String spanishDes = "";
        String japaneseDes = "";
        String frenchDes = "";

        while(counter != size-1 && (englishDes.isEmpty() || spanishDes.isEmpty()  || japaneseDes.isEmpty() || frenchDes.isEmpty())){
            String language = pokemonSpeciesDTO
                    .getFlavor_text_entries()
                    .get(counter)
                    .getLanguage()
                    .getName();

            String description = pokemonSpeciesDTO
                    .getFlavor_text_entries()
                    .get(counter)
                    .getFlavor_text()
                    .replaceAll("\n"," ")
                    .replaceAll("\r"," ");

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

        if(spanishDes.isEmpty()){
            spanishDes = englishDes;
        } else if (japaneseDes.isEmpty()) {
            japaneseDes = englishDes;
        } else if (frenchDes.isEmpty()) {
            frenchDes = englishDes;
        }

        return Description.builder()
                .d_english(englishDes)
                .d_french(frenchDes)
                .d_japanese(japaneseDes)
                .d_spanish(spanishDes)
                .build();
    }
}
