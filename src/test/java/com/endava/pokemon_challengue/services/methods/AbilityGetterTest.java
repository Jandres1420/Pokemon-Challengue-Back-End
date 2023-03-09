package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Ability;
import com.endava.pokemon_challengue.models.dto.AbilityDTO;
import com.endava.pokemon_challengue.models.dto.ability.EffectDTO;
import com.endava.pokemon_challengue.models.dto.ability.NameDTO;
import com.endava.pokemon_challengue.models.dto.language.LanguageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbilityGetterTest {

    @InjectMocks
    AbilityGetter abilityGetter;

    @Test
    void Given_LanguageIsSpanish_When_PokemonSpeciesExists_Then_ReturnDescriptionAndNameInSpanish() {
        List<NameDTO> names = new ArrayList<>();
        List<EffectDTO> effects = new ArrayList<>();

        names.add(NameDTO.builder()
                .name("Habilidad 1").language(LanguageDTO.builder().name("es").build())
                .build());

        effects.add(EffectDTO.builder().effect("Efecto 1").language(LanguageDTO.builder().name("es").build()).build());

        AbilityDTO abilityDTO = AbilityDTO.builder()
                .names(names)
                .effect_entries(effects)
                .build();

        Ability ability = abilityGetter.getAbility(abilityDTO);

        Assertions.assertEquals(ability.getN_spanish(),"Habilidad 1");
        Assertions.assertEquals(ability.getD_spanish(),"Efecto 1");
    }
}