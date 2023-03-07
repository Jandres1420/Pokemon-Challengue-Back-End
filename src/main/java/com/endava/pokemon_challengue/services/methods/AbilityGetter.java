package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Ability;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class AbilityGetter {

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
