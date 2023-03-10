package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.exceptions.custom.InvalidRole;
import com.endava.pokemon_challengue.exceptions.custom.InvalidValue;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.models.dto.abilityUrl.AbilitiesUrlDTO;
import com.endava.pokemon_challengue.models.dto.abilityUrl.AbilityUrlDTO;
import com.endava.pokemon_challengue.models.dto.requestBody.DeletePokemonRequest;
import com.endava.pokemon_challengue.models.dto.requestBody.UpdatePokemonRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.CRUDResponse;
import com.endava.pokemon_challengue.services.PokemonApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PokemonControllerTest {

    @Mock
    PokemonApiService pokemonApiServiceMock;

    @Mock
    RestTemplate restTemplateMock;


    @InjectMocks
    PokemonController pokemonController;

    @Test
    void Given_PokemonDTO_When_GetAbilities_Then_Success() {
        List<AbilitiesUrlDTO> abilitiesUrl = new ArrayList<>();
        abilitiesUrl.add(AbilitiesUrlDTO.builder()
                .ability(AbilityUrlDTO.builder().url("ability.com").build())
                .build());

        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .abilities(abilitiesUrl)
                .build();

        when(restTemplateMock.getForObject("ability.com",AbilityDTO.class)).thenReturn(AbilityDTO.builder().id(9).build());

        List<AbilityDTO> abilities = pokemonController.getAbilitiesDTO(pokemonDTO);

        Assertions.assertEquals(abilities.size(), 1);
        Assertions.assertEquals(abilities.get(0).getId(), 9);

    }

    @Test
    void Given_ValidNicknameAndUserConnected_When_UpdatingPokemon_Then_SuccessMessage() {
        long captureId = 1;
        String nickname = "new nickname";
        String username = "gabriel";

        when(pokemonApiServiceMock.updatePokemon(captureId,nickname,username)).thenReturn(CRUDResponse
                .builder()
                        .responseCode("Ok")
                        .responseMessage("Pokemon updated to new nickname")
                .build());

        CRUDResponse crudResponse = pokemonController.updatePokemon("gabriel", UpdatePokemonRequest
                .builder()
                        .id(captureId)
                        .nickname("new nickname")
                .build(), "gabriel");

        Assertions.assertEquals(crudResponse.getResponseCode(), "Ok");
        Assertions.assertEquals(crudResponse.getResponseMessage(), "Pokemon updated to new nickname");
    }

    @Test
    void Given_EmptyOrNullNickname_WhenUpdatingPokemon_Then_ThrowException() {
        long captureId = 1;

        Exception exceptionNull = Assertions.assertThrows(InvalidValue.class,()->{
                    CRUDResponse crudResponse = pokemonController.updatePokemon("gabriel", UpdatePokemonRequest
                            .builder()
                            .id(captureId)
                            .nickname("")
                            .build(), "gabriel");});

        Assertions.assertEquals("The nickname is mandatory", exceptionNull.getMessage());
    }

    @Test
    void Given_WrongUsernameConnected_WhenUpdatingPokemon_Then_ThrowException() {
        long captureId = 1;

        Exception exceptionRole = Assertions.assertThrows(InvalidRole.class,()->{
            CRUDResponse crudResponse = pokemonController.updatePokemon("gabriel", UpdatePokemonRequest
                    .builder()
                    .id(captureId)
                    .nickname("")
                    .build(), "andres");});

        Assertions.assertEquals("You are not allowed to this Pokedex", exceptionRole.getMessage());
    }

    @Test
    void Given_PokemonName_When_GetPokemonSpecies_Then_Success() {
        when(restTemplateMock.getForObject("https://pokeapi.co/api/v2/pokemon-species/bulbasaur",
                PokemonSpeciesDTO.class)).thenReturn(PokemonSpeciesDTO.builder().build());

        PokemonSpeciesDTO pokemonSpeciesDTO = pokemonController.getPokemonSpeciesDTO("bulbasaur");
        Assertions.assertNotNull(pokemonSpeciesDTO);
    }

    @Test
    void Given_UsernameIsConnected_When_ReleasePokemon_Then_Success() {
        long captureId = 5;
        String username = "gabriel";

        when(pokemonApiServiceMock.releasePokemon(captureId, username)).thenReturn(CRUDResponse
                .builder()
                        .responseMessage("Pokemon released")
                        .responseCode("Perfect")
                .build());

        CRUDResponse crudResponse = pokemonController.releasePokemon(username, DeletePokemonRequest
                .builder()
                .id(captureId)
                .build(), "gabriel");

        Assertions.assertEquals(crudResponse.getResponseMessage(), "Pokemon released");
        Assertions.assertEquals(crudResponse.getResponseCode(), "Perfect");
    }

    @Test
    void Given_UsernameIsNotConnected_When_ReleasePokemon_Then_ThrowException() {
        long captureId = 5;
        String username = "gabriel";

        Exception exceptionRole = Assertions.assertThrows(InvalidRole.class,()->{
            CRUDResponse crudResponse = pokemonController.releasePokemon(username, DeletePokemonRequest
                    .builder()
                    .id(captureId)
                    .build(), "andres");});

            Assertions.assertEquals("You are not allowed to this Pokedex", exceptionRole.getMessage());
    }
}