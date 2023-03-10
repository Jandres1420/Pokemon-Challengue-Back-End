package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.models.Ability;
import com.endava.pokemon_challengue.models.Description;
import com.endava.pokemon_challengue.models.Pokemon;
import com.endava.pokemon_challengue.models.Stat;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.repositories.CaptureRepository;
import com.endava.pokemon_challengue.repositories.PokemonRepository;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import com.endava.pokemon_challengue.services.methods.AbilityGetter;
import com.endava.pokemon_challengue.services.methods.DescriptionGetter;
import com.endava.pokemon_challengue.services.methods.PokemonGetter;
import com.endava.pokemon_challengue.services.methods.StatGetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PokemonApiServiceTest {
    @Captor
    ArgumentCaptor<Pokemon> pokemonArgumentCaptor;
    @Mock
    PokemonRepository pokemonRepository;
    @Mock
    PokemonGetter pokemonGetter;
    @Mock
    StatGetter statGetter;
    @Mock
    AbilityGetter abilityGetter;
    @Mock
    DescriptionGetter descriptionGetter;
    @InjectMocks
    PokemonApiService pokemonApiService;
    @Test
    void addPokemonDB() {

        PokemonDTO pokemonDTO = new PokemonDTO();
        PokemonSpeciesDTO pokemonSpeciesDTO = new PokemonSpeciesDTO();
        List<AbilityDTO> abilitiesDTO = new ArrayList<>();
        abilitiesDTO.add(new AbilityDTO());

        when(pokemonGetter.getPokemon(any(),any())).thenReturn(Pokemon.builder().pokemon_id(200).build());
        when(statGetter.getStat(any())).thenReturn(Stat.builder().attack(100).build());
        when(descriptionGetter.getDescription(any())).thenReturn(Description.builder().d_spanish("HOLA").build());
        when(abilityGetter.getAbility(any())).thenReturn(Ability.builder().d_spanish("Placa").build());

        pokemonApiService.addPokemonDB(pokemonDTO, pokemonSpeciesDTO, abilitiesDTO);

        //
        verify(pokemonRepository).save(pokemonArgumentCaptor.capture());
        Assertions.assertNotNull(pokemonArgumentCaptor.getValue());
    }
}