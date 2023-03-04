package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Ability;
import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;

public class PokemonLanguage {


    public Ability getAbility(AbilityDTO abilityDTO) {
        //Get Ability Description
        int descriptionCounter = 0;
        int descriptionsSize = abilityDTO.getEffect_entries().size();

        String englishDes = "";
        String spanishDes = "";
        String japaneseDes = "";
        String germanDes = "";




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
        return null;
    }
}
