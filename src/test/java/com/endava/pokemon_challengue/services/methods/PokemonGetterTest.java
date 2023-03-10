package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Pokemon;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.models.dto.dashboard.PokemonResponseDTO;
import com.endava.pokemon_challengue.models.dto.dashboard.ResultDTO;
import com.endava.pokemon_challengue.models.dto.dashboard.ResultsDTO;
import com.endava.pokemon_challengue.models.dto.evolution.EvolutionUrlDTO;
import com.endava.pokemon_challengue.models.dto.image.DreamWorldDTO;
import com.endava.pokemon_challengue.models.dto.image.OtherDTO;
import com.endava.pokemon_challengue.models.dto.image.SpritesDTO;
import com.endava.pokemon_challengue.models.dto.type.TypeDTO;
import com.endava.pokemon_challengue.models.dto.type.TypesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PokemonGetterTest {

    @InjectMocks
    PokemonGetter pokemonGetter;

    @Test
    void Given_PokemonDTOAndPokemonSpeciesDTO_When_GetPokemon_Then_ReturnPokemon() {
        ArrayList<TypesDTO> types = new ArrayList<>();
        types.add(TypesDTO.builder().type(TypeDTO.builder().name("electric").build()).build());

        SpritesDTO sprites = SpritesDTO.builder()
                .other(OtherDTO.builder()
                        .dream_world(DreamWorldDTO
                                .builder().front_default("image pokemon")
                                .build()).build()).build();


        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name("pikachu")
                .id(25)
                .types(types)
                .sprites(sprites)
                .build();

        PokemonSpeciesDTO pokemonSpeciesDTO = PokemonSpeciesDTO.builder()
                .evolution_chain(EvolutionUrlDTO.builder().url("evolution.com").build())
                .build();

        Pokemon pokemon = pokemonGetter.getPokemon(pokemonDTO, pokemonSpeciesDTO);

        Assertions.assertEquals(pokemon.getPokemon_id(), 25);
    }

    @Test
    void Given_PokemonResponseDTO_When_GetPokemon_Then_ReturnPokemon() {
        PokemonResponseDTO pokemonResponseDTO = PokemonResponseDTO.builder()
                .name("pikachu")
                .id(25)
                .types(List.of("electric","normal"))
                .img_path("x")
                .build();
        ResultDTO resultDTO = ResultDTO.builder().name("Results").build();
        ResultsDTO resultsDTO = ResultsDTO.builder().results(List.of(resultDTO)).build();
        Assertions.assertEquals("pikachu",pokemonResponseDTO.getName());
        Assertions.assertEquals("Results",resultsDTO.getResults().get(0).getName());
    }

}