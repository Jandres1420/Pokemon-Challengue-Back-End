package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Description;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.models.dto.description.FlavorTextDTO;
import com.endava.pokemon_challengue.models.dto.language.LanguageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DescriptionGetterTest {

    @InjectMocks
    DescriptionGetter descriptionGetter;

    @Test
    void Given_LanguageIsSpanish_When_PokemonSpeciesExists_Then_ReturnDescriptionInSpanish() {

        List<FlavorTextDTO> flavor_text = new ArrayList<>();
        flavor_text.add(FlavorTextDTO.builder().flavor_text("Descripción Español").language(LanguageDTO.builder().name("es").build()).build());

        PokemonSpeciesDTO pokemonSpeciesDTO = PokemonSpeciesDTO.builder()
                .flavor_text_entries(flavor_text)
                .build();

        Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);

        Assertions.assertEquals(description.getD_spanish(), "Descripción Español");
    }

    @Test
    void Given_LanguageIsEnglish_When_PokemonSpeciesExists_Then_ReturnDescriptionInEnglish() {

        List<FlavorTextDTO> flavor_text = new ArrayList<>();
        flavor_text.add(FlavorTextDTO.builder().flavor_text("Description in English").language(LanguageDTO.builder().name("en").build()).build());

        PokemonSpeciesDTO pokemonSpeciesDTO = PokemonSpeciesDTO.builder()
                .flavor_text_entries(flavor_text)
                .build();

        Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);

        Assertions.assertEquals(description.getD_english(), "Description in English");
    }

    @Test
    void Given_LanguageIsJapanese_When_PokemonSpeciesExists_Then_ReturnDescriptionInJapanese() {

        List<FlavorTextDTO> flavor_text = new ArrayList<>();
        flavor_text.add(FlavorTextDTO.builder().flavor_text("!!").language(LanguageDTO.builder().name("ja").build()).build());

        PokemonSpeciesDTO pokemonSpeciesDTO = PokemonSpeciesDTO.builder()
                .flavor_text_entries(flavor_text)
                .build();

        Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);

        Assertions.assertEquals(description.getD_japanese(), "!!");
    }

    @Test
    void Given_LanguageIsGerman_When_PokemonSpeciesExists_Then_ReturnDescriptionInGerman() {

        List<FlavorTextDTO> flavor_text = new ArrayList<>();
        flavor_text.add(FlavorTextDTO.builder().flavor_text("Deutsch ist die Beste").language(LanguageDTO.builder().name("de").build()).build());

        PokemonSpeciesDTO pokemonSpeciesDTO = PokemonSpeciesDTO.builder()
                .flavor_text_entries(flavor_text)
                .build();

        Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);

        Assertions.assertEquals(description.getD_german(), "Deutsch ist die Beste");
    }
}