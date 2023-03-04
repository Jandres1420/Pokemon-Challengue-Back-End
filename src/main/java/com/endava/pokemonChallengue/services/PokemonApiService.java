package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Ability;
import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.stat.StatsDTO;
import com.endava.pokemonChallengue.repositories.AbilityRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonApiService {
    private final PokemonRepository pokemonRepository;
    private final AbilityRepository abilityRepository;

    public void pokemonService(PokemonDTO pokemonDTO, PokemonSpeciesDTO pokemonSpeciesDTO, List<AbilityDTO> abilitiesDTO, String name) {

        //todo CHEQUEAR SI EL POKEMON EXISTE
//        if(pokemonRepository.findByPokemon_id(Integer.parseInt(name)).isPresent()){
//            throw ExceptionGenerator.getException(ExceptionType.PARAMS_REQUIRED, "Enter a valid id");
//        }

        Pokemon pokemon = getPokemon(pokemonDTO);

        Stat stat = getStat(pokemonDTO);
        stat.setPokemon(pokemon);
        pokemon.setStat(stat);

        Description description = getDescription(pokemonSpeciesDTO);
        description.setPokemon(pokemon);
        pokemon.setDescription(description);

        for (int i = 0; i < abilitiesDTO.size(); i++) {
            Ability ability = getAbility(abilitiesDTO.get(i));
            System.out.println(ability.toString());
            abilityRepository.save(ability);
        }

        pokemonRepository.save(pokemon);
    }

    public Pokemon getPokemon(PokemonDTO pokemonDTO) {
        String types = "";
        for (int i = 0; i < pokemonDTO.getTypes().size(); i++) {
            types += pokemonDTO.getTypes().get(i).getType().getName() + ", ";
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

    public Stat getStat(PokemonDTO pokemonDTO) {
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

    public Description getDescription(PokemonSpeciesDTO pokemonSpeciesDTO) {
        int counter = 0;
        int size = pokemonSpeciesDTO.getFlavor_text_entries().size();

        String englishDes = "";
        String spanishDes = "";
        String japaneseDes = "";
        String germanDes = "";

        while (counter != size && (englishDes.isEmpty() || spanishDes.isEmpty() || japaneseDes.isEmpty() || germanDes.isEmpty())) {
            String language = pokemonSpeciesDTO
                    .getFlavor_text_entries()
                    .get(counter)
                    .getLanguage()
                    .getName();

            String description = pokemonSpeciesDTO
                    .getFlavor_text_entries()
                    .get(counter)
                    .getFlavor_text()
                    .replace("\n", " ")
                    .replace("\r", " ");

            if (language.equals("en") && englishDes.isEmpty()) {
                englishDes = description;
            }
            if (language.equals("es") && spanishDes.isEmpty()) {
                spanishDes = description;
            }
            if (language.equals("ja") && japaneseDes.isEmpty()) {
                japaneseDes = description;
            }
            if (language.equals("de") && germanDes.isEmpty()) {
                germanDes = description;
            }
            counter++;
        }

        if (spanishDes.isEmpty()) {
            spanishDes = englishDes;
        }
        if (japaneseDes.isEmpty()) {
            japaneseDes = englishDes;
        }
        if (germanDes.isEmpty()) {
            germanDes = englishDes;
        }

        return Description.builder()
                .d_english(englishDes)
                .d_german(germanDes)
                .d_japanese(japaneseDes)
                .d_spanish(spanishDes)
                .build();
    }

    public Ability getAbility(AbilityDTO abilityDTO) {
        //Get Ability Description
        int descriptionCounter = 0;
        int descriptionsSize = abilityDTO.getEffect_entries().size();

        String englishDes = "";
        String spanishDes = "";
        String japaneseDes = "";
        String germanDes = "";

        while(descriptionCounter != descriptionsSize && (englishDes.isEmpty() || spanishDes.isEmpty()  || japaneseDes.isEmpty() || germanDes.isEmpty())){
            String language = abilityDTO
                    .getEffect_entries()
                    .get(descriptionCounter)
                    .getLanguage()
                    .getName();


            String description = abilityDTO
                    .getEffect_entries()
                    .get(descriptionCounter)
                    .getEffect()
                    .replace("\n"," ")
                    .replace("\r"," ");

            if(language.equals("en") && englishDes.isEmpty()){
                englishDes = description;
            }
            if (language.equals("es") && spanishDes.isEmpty()) {
                spanishDes = description;
            }
            if (language.equals("ja") && japaneseDes.isEmpty()) {
                japaneseDes = description;
            }
            if (language.equals("de") && germanDes.isEmpty()){
                germanDes = description;
            }
            descriptionCounter++;
        }

        if(spanishDes.isEmpty()){
            spanishDes = englishDes;
        }
        if (japaneseDes.isEmpty()) {
            japaneseDes = englishDes;
        }
        if (germanDes.isEmpty()) {
            germanDes = englishDes;
        }

        //Get Ability Name
        int nameCounter = 0;
        int namesSize = abilityDTO.getNames().size();

        String englishName = "";
        String spanishName = "";
        String japaneseName = "";
        String germanName = "";

        while(nameCounter != namesSize && (englishName.isEmpty() || spanishName.isEmpty()  || japaneseName.isEmpty() || germanName.isEmpty())){
            String language = abilityDTO
                    .getNames()
                    .get(nameCounter)
                    .getLanguage()
                    .getName();

            String name = abilityDTO
                    .getNames()
                    .get(nameCounter)
                    .getName()
                    .replace("\n"," ")
                    .replace("\r"," ");

            if(language.equals("en") && englishName.isEmpty()){
                englishName = name;
            }
            if (language.equals("es") && spanishName.isEmpty()) {
                spanishName = name;
            }
            if (language.equals("ja") && japaneseName.isEmpty()) {
                japaneseName = name;
            }
            if (language.equals("de") && germanName.isEmpty()){
                germanName = name;
            }
            nameCounter++;
        }

        if(spanishName.isEmpty()){
            spanishName = englishName;
        }
        if (japaneseName.isEmpty()) {
            japaneseName = englishName;
        }
        if (germanName.isEmpty()) {
            germanName = englishName;
        }

        return Ability.builder()
                .ability_id(abilityDTO.getId())
                .d_english(englishDes)
                .d_german(germanDes)
                .d_japanese(japaneseDes)
                .d_spanish(spanishDes)
                .n_english(englishName)
                .n_spanish(spanishName)
                .n_german(germanName)
                .n_japanese(japaneseName)
                .build();
    }

}
