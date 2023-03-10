package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Description;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class DescriptionGetter {

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

            if (language.equals("en") && englishDes.isEmpty()) englishDes = description;
            if (language.equals("es") && spanishDes.isEmpty()) spanishDes = description;
            if (language.equals("ja") && japaneseDes.isEmpty()) japaneseDes = description;
            if (language.equals("de") && germanDes.isEmpty()) germanDes = description;
            counter++;
        }

        if (spanishDes.isEmpty()) spanishDes = englishDes;
        if (japaneseDes.isEmpty()) japaneseDes = englishDes;
        if (germanDes.isEmpty()) germanDes = englishDes;

        return Description.builder()
                .d_english(englishDes)
                .d_german(germanDes)
                .d_japanese(japaneseDes)
                .d_spanish(spanishDes)
                .build();
    }
}
