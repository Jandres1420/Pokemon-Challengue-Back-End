package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Ability;
import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.repositories.CaptureRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.UserRepository;
import com.endava.pokemonChallengue.services.methods.AbilityGetter;
import com.endava.pokemonChallengue.services.methods.DescriptionGetter;
import com.endava.pokemonChallengue.services.methods.PokemonGetter;
import com.endava.pokemonChallengue.services.methods.StatGetter;
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

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PokemonApiServiceTest {
    @Captor
    ArgumentCaptor<Pokemon> pokemonArgumentCaptor;
    @Mock
    PokemonRepository pokemonRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CaptureRepository captureRepository;
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