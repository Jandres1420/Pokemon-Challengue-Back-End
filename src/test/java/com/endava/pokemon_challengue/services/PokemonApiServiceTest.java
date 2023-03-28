package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.models.*;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import com.endava.pokemon_challengue.models.dto.EvolutionDTO;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemon_challengue.models.dto.ability.EffectDTO;
import com.endava.pokemon_challengue.models.dto.ability.NameDTO;
import com.endava.pokemon_challengue.models.dto.evolution.ChainDTO;
import com.endava.pokemon_challengue.models.dto.evolution.EvolvesToDTO;
import com.endava.pokemon_challengue.models.dto.evolution.SpeciesDTO;
import com.endava.pokemon_challengue.models.dto.language.LanguageDTO;
import com.endava.pokemon_challengue.models.dto.responseBody.CRUDResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.EvolutionChainResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.EvolutionResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SinglePokemonDetailsResponse;
import com.endava.pokemon_challengue.models.dto.stat.StatDTO;
import com.endava.pokemon_challengue.models.dto.stat.StatsDTO;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PokemonApiServiceTest {
    @Captor
    ArgumentCaptor<Pokemon> pokemonArgumentCaptor;

    @Captor
    ArgumentCaptor<Capture> captureArgumentCaptor;

    @Mock
    PokemonRepository pokemonRepository;

    @Mock
    UserProfileRepository userProfileRepository;

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
    void Given_AllArguments_When_AddPokemonDB_Then_CheckPokemonWasAddedCorrectly() {

        PokemonDTO pokemonDTO = new PokemonDTO();
        PokemonSpeciesDTO pokemonSpeciesDTO = new PokemonSpeciesDTO();
        List<AbilityDTO> abilitiesDTO = new ArrayList<>();
        abilitiesDTO.add(new AbilityDTO());

        when(pokemonGetter.getPokemon(any(),any())).thenReturn(Pokemon.builder().pokemon_id(200).build());
        when(statGetter.getStat(any())).thenReturn(Stat.builder().attack(100).build());
        when(descriptionGetter.getDescription(any())).thenReturn(Description.builder().d_spanish("Descripción").build());
        when(abilityGetter.getAbility(any())).thenReturn(Ability.builder().d_spanish("Habilidad").build());

        pokemonApiService.addPokemonDB(pokemonDTO, pokemonSpeciesDTO, abilitiesDTO);

        verify(pokemonRepository).save(pokemonArgumentCaptor.capture());
        Assertions.assertNotNull(pokemonArgumentCaptor.getValue());
        Assertions.assertEquals(pokemonArgumentCaptor.getValue().getDescription().getD_spanish(), "Descripción");
    }

    @Test
    void Given_SpeciesAndLanguage_When_BuildSpecies_Then_ReturnSpeciesSuccessfully() {
        SpeciesDTO species = SpeciesDTO.builder().name("pikachu").build();

        EvolutionChainResponse evolutionChainResponse = pokemonApiService.buildSpecies(species,"en");

        Assertions.assertEquals(evolutionChainResponse.getName(), "pikachu");
    }

    @Test
    void Given_EvolutionDTOAndLanguage_When_PokemonNoEvolution_Then_ReturnEvolutionChainAndNextEvolution() {
        List<EvolvesToDTO> evolves = new ArrayList<>();
        evolves.add(EvolvesToDTO.builder().build());
        EvolutionDTO evolutionDTO = EvolutionDTO.builder()
                .chain(ChainDTO.builder()
                        .evolves_to(evolves)
                        .species(SpeciesDTO.builder()
                                .name("ditto").build())
                        .build()).build();

        EvolutionResponse evolutionResponse = pokemonApiService.pokemonNoEvolution(evolutionDTO, "de",null);
        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(0).getName(),"ditto");
        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(0).getDetailed_url(),"/pokedex/de/pokemon/ditto");
    }

    @Test
    void Given_PokemonName_When_PokemonNotInDB_Then_ReturnEmpty() {
        String response = pokemonApiService.findEvolutionUrl("ditto");
        Assertions.assertEquals(response, "");
    }

    @Test
    void Given_PokemonName_When_PokemonInDB_Then_ReturnURL() {
        when(pokemonRepository
                .findPokemonByName("ditto"))
                .thenReturn(Optional.of(Pokemon.builder().evolution_url("ditto.com").build()));

        String response = pokemonApiService.findEvolutionUrl("ditto");

        Assertions.assertEquals(response, "ditto.com");
    }

    @Test
    void Given_EvolutionDTOAndLanguage_When_PokemonBranchEvolution_Then_ReturnEvolutionChainAndNextEvolution() {
        List<EvolvesToDTO> evolves = new ArrayList<>();

        evolves.add(EvolvesToDTO.builder()
                        .species(SpeciesDTO.builder().name("flareon").build())
                .build());

        EvolutionDTO evolutionDTO = EvolutionDTO.builder()
                .chain(ChainDTO.builder()
                        .species(SpeciesDTO.builder().name("eevee").build())
                        .evolves_to(evolves)
                        .build())
                .build();

        EvolutionResponse evolutionResponse = pokemonApiService.pokemonBranchEvolution(evolutionDTO, "en");

        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(0).getName(), "eevee");
        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(1).getName(), "flareon");

    }

    @Test
    void Given_EvolutionDTOAndLanguage_When_PokemonSequenceEvolution_Then_ReturnEvolutionChainAndNextEvolution() {
        List<EvolvesToDTO> evolves = new ArrayList<>();
        List<EvolvesToDTO> evolves2 = new ArrayList<>();

        evolves2.add(EvolvesToDTO.builder()
                .species(SpeciesDTO.builder().name("raichu").build())
                .evolves_to(new ArrayList<>())
                .build());

        evolves.add(EvolvesToDTO.builder()
                .species(SpeciesDTO.builder().name("pikachu").build())
                        .evolves_to(evolves2)
                .build());

        EvolutionDTO evolutionDTO = EvolutionDTO.builder()
                .chain(ChainDTO.builder()
                        .species(SpeciesDTO.builder().name("pichu").build())
                        .evolves_to(evolves)
                        .build())
                .build();

        EvolutionResponse evolutionResponse = pokemonApiService.pokemonSequenceEvolution(evolutionDTO, "en", "pichu",null);

        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(0).getName(), "pichu");
        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(1).getName(), "pikachu");
        Assertions.assertEquals(evolutionResponse.getEvolution_chain().get(2).getName(), "raichu");

    }

    @Test
    void Given_LanguageIsEnglish_When_PokemonExists_Then_GetAllInEnglish() {
        when(pokemonGetter.getPokemon(any(),any())).thenReturn(Pokemon.builder().name("bulbasaur").type("poison, grass").build());
        when(statGetter.getStat(any())).thenReturn(Stat.builder()
                        .attack(1)
                        .health(2)
                        .defense(3)
                        .speed(4)
                        .specialAttack(5)
                        .specialDefense(6)
                .build());
        when(descriptionGetter.getDescription(any())).thenReturn(Description.builder().d_english("D English").build());
        when(abilityGetter.getAbility(any())).thenReturn(Ability.builder().n_english("A English").build());

        List<NameDTO> names = new ArrayList<>();
        names.add(NameDTO.builder().name("Ability").language(LanguageDTO.builder().name("en").build()).build());

        List<EffectDTO> effects = new ArrayList<>();
        effects.add(EffectDTO.builder().effect("Effect").language(LanguageDTO.builder().name("en").build()).build());

        List<AbilityDTO> abilities = new ArrayList<>();
        abilities.add(AbilityDTO.builder().names(names).effect_entries(effects).build());

        SinglePokemonDetailsResponse singlePokemonDetailsResponse= pokemonApiService.pokemonDetails(
                any(PokemonDTO.class),
                any(PokemonSpeciesDTO.class),
                abilities,
               "en");

        Assertions.assertEquals(singlePokemonDetailsResponse.getDescription(), "D English");
        Assertions.assertEquals(singlePokemonDetailsResponse.getAbilities().get(0).getName(), "A English");


    }

    @Test
    void Given_LanguageIsSpanish_When_PokemonExists_Then_GetAllInSpanish() {
        when(pokemonGetter.getPokemon(any(),any())).thenReturn(Pokemon.builder().name("bulbasaur").type("poison, grass").build());
        when(statGetter.getStat(any())).thenReturn(Stat.builder()
                .attack(1)
                .health(2)
                .defense(3)
                .speed(4)
                .specialAttack(5)
                .specialDefense(6)
                .build());
        when(descriptionGetter.getDescription(any())).thenReturn(Description.builder().d_spanish("D Spanish").build());
        when(abilityGetter.getAbility(any())).thenReturn(Ability.builder().n_spanish("A Spanish").build());

        List<NameDTO> names = new ArrayList<>();
        names.add(NameDTO.builder().name("Habilidad").language(LanguageDTO.builder().name("es").build()).build());

        List<EffectDTO> effects = new ArrayList<>();
        effects.add(EffectDTO.builder().effect("Efecto").language(LanguageDTO.builder().name("es").build()).build());

        List<AbilityDTO> abilities = new ArrayList<>();
        abilities.add(AbilityDTO.builder().names(names).effect_entries(effects).build());

        SinglePokemonDetailsResponse singlePokemonDetailsResponse= pokemonApiService.pokemonDetails(
                any(PokemonDTO.class),
                any(PokemonSpeciesDTO.class),
                abilities,
                "es");

        Assertions.assertEquals(singlePokemonDetailsResponse.getDescription(), "D Spanish");
        Assertions.assertEquals(singlePokemonDetailsResponse.getAbilities().get(0).getName(), "A Spanish");
    }

    @Test
        void Given_LanguageIsGerman_When_PokemonExists_Then_GetAllInGerman() {
        when(pokemonGetter.getPokemon(any(),any())).thenReturn(Pokemon.builder().name("bulbasaur").type("poison, grass").build());
        when(statGetter.getStat(any())).thenReturn(Stat.builder()
                .attack(1)
                .health(2)
                .defense(3)
                .speed(4)
                .specialAttack(5)
                .specialDefense(6)
                .build());
        when(descriptionGetter.getDescription(any())).thenReturn(Description.builder().d_german("D German").build());
        when(abilityGetter.getAbility(any())).thenReturn(Ability.builder().n_german("A German").build());

        List<NameDTO> names = new ArrayList<>();
        names.add(NameDTO.builder().name("Abilität").language(LanguageDTO.builder().name("de").build()).build());

        List<EffectDTO> effects = new ArrayList<>();
        effects.add(EffectDTO.builder().effect("Efëct").language(LanguageDTO.builder().name("de").build()).build());

        List<AbilityDTO> abilities = new ArrayList<>();
        abilities.add(AbilityDTO.builder().names(names).effect_entries(effects).build());

        SinglePokemonDetailsResponse singlePokemonDetailsResponse= pokemonApiService.pokemonDetails(
                any(PokemonDTO.class),
                any(PokemonSpeciesDTO.class),
                abilities,
                "de");

        Assertions.assertEquals(singlePokemonDetailsResponse.getDescription(), "D German");
        Assertions.assertEquals(singlePokemonDetailsResponse.getAbilities().get(0).getName(), "A German");

    }

    @Test
    void Given_LanguageIsJapanese_When_PokemonExists_Then_GetAllInJapanese() {
        when(pokemonGetter.getPokemon(any(),any())).thenReturn(Pokemon.builder().name("bulbasaur").type("poison, grass").build());
        when(statGetter.getStat(any())).thenReturn(Stat.builder()
                .attack(1)
                .health(2)
                .defense(3)
                .speed(4)
                .specialAttack(5)
                .specialDefense(6)
                .build());
        when(descriptionGetter.getDescription(any())).thenReturn(Description.builder().d_japanese("D Japanese").build());
        when(abilityGetter.getAbility(any())).thenReturn(Ability.builder().n_japanese("A Japanese").build());

        List<NameDTO> names = new ArrayList<>();
        names.add(NameDTO.builder().name("!!").language(LanguageDTO.builder().name("ja").build()).build());

        List<EffectDTO> effects = new ArrayList<>();
        effects.add(EffectDTO.builder().effect("!!!").language(LanguageDTO.builder().name("ja").build()).build());

        List<AbilityDTO> abilities = new ArrayList<>();
        abilities.add(AbilityDTO.builder().names(names).effect_entries(effects).build());

        SinglePokemonDetailsResponse singlePokemonDetailsResponse= pokemonApiService.pokemonDetails(
                any(PokemonDTO.class),
                any(PokemonSpeciesDTO.class),
                abilities,
                "ja");

        Assertions.assertEquals(singlePokemonDetailsResponse.getDescription(), "D Japanese");
        Assertions.assertEquals(singlePokemonDetailsResponse.getAbilities().get(0).getName(), "A Japanese");

    }

    @Test
    void Given_Username_When_UpdatePokemonAndUserDoesNotExist_Then_ReturnFailResponse() {
        long id = 2;
        CRUDResponse crudResponse = pokemonApiService.updatePokemon(id, "new Nickname", "nickname");

        Assertions.assertEquals(crudResponse.getResponseMessage(), "That trainer does not have that pokemon");
        Assertions.assertEquals(crudResponse.getResponseCode(), "Error");

    }

    @Test
    void Given_Username_When_UpdatePokemonAndUserExist_Then_ReturnSuccessResponse() {
        long capture_id = 1;
        int user_id = 3;
        when(userProfileRepository.findByUsername(any())).thenReturn(Optional.of(UserProfile
                .builder()
                .username("gabriel")
                .user_id(user_id)
                .build()));

        when(captureRepository.findCaptureByCaptureIdAndUserId(capture_id,user_id))
                .thenReturn(Optional.of(Capture.builder().nickname("nickname").build()));

        CRUDResponse crudResponse = pokemonApiService.updatePokemon(capture_id, "New Nickname", "gabriel");

        verify(captureRepository).save(captureArgumentCaptor.capture());

        Assertions.assertEquals(captureArgumentCaptor.getValue().getNickname(), "New Nickname");
        Assertions.assertEquals(crudResponse.getResponseCode(), "Ok");
        Assertions.assertEquals(crudResponse.getResponseMessage(), "Pokemon nickname updated to New Nickname");
    }


    @Test
    void Given_Username_When_ReleasePokemonAndUserDoesNotExist_Then_ReturnFailResponse() {
        long capture_id = 2;

        CRUDResponse crudResponse = pokemonApiService.releasePokemon(capture_id, "gabriel");
        Assertions.assertEquals(crudResponse.getResponseMessage(),"That trainer does not have that pokemon");
        Assertions.assertEquals(crudResponse.getResponseCode(),"Error");

    }

    @Test
    void Given_Username_When_ReleasePokemonAndUserExists_Then_ReturnSuccessResponse() {
        int user_id = 3;
        long capture_id = 2;

        when(userProfileRepository.findByUsername(any())).thenReturn(Optional.of(UserProfile
                .builder()
                .username("gabriel")
                .user_id(user_id)
                .build()));

        when(captureRepository.findCaptureByCaptureIdAndUserId(capture_id,user_id))
                .thenReturn(Optional.of(Capture.builder().nickname("BulbaGOD").build()));

        CRUDResponse crudResponse = pokemonApiService.releasePokemon(capture_id, "gabriel");

        Assertions.assertEquals(crudResponse.getResponseMessage(),"Pokemon BulbaGOD deleted.");
        Assertions.assertEquals(crudResponse.getResponseCode(),"Ok");
    }

    @Test
    void Given_Arguments_When_PokemonInDB_Then_DontSaveInDBAgain() {
        String username = "gabriel";
        List<NameDTO> names = new ArrayList<>();
        names.add(NameDTO.builder().name("Habilidad").language(LanguageDTO.builder().name("es").build()).build());

        List<StatsDTO> stats = new ArrayList<>();
        stats.add(StatsDTO.builder().base_stat(100).stat(StatDTO.builder().name("health").build()).build());

        PokemonDTO pokemonDTO = PokemonDTO.builder().stats(stats).build();
        PokemonSpeciesDTO pokemonSpeciesDTO = new PokemonSpeciesDTO();
        List<EffectDTO> effects = new ArrayList<>();
        effects.add(EffectDTO.builder().effect("Efecto").language(LanguageDTO.builder().name("es").build()).build());

        List<AbilityDTO> abilities = new ArrayList<>();
        abilities.add(AbilityDTO.builder().names(names).effect_entries(effects).build());

        when(userProfileRepository.findByUsername(username)).thenReturn(Optional.of(UserProfile.builder().username(username).connect(true).build()));
        when(pokemonRepository.findPokemonByName("bulbasaur")).thenReturn(Optional.of(Pokemon.builder().name("bulbasaur").build()));

        CRUDResponse crudResponse = pokemonApiService.pokemonCapture(
                username,
                "bulbasaur",
                "BulbaGOD",
                pokemonDTO,
                pokemonSpeciesDTO,
                abilities);

        verify(captureRepository).save(captureArgumentCaptor.capture());
        Assertions.assertEquals(captureArgumentCaptor.getValue().getNickname(), "BulbaGOD");
        Assertions.assertEquals(crudResponse.getResponseCode(), "Ok");
        Assertions.assertEquals(crudResponse.getResponseMessage(), "Pokemon bulbasaur added to gabriel");
    }
}