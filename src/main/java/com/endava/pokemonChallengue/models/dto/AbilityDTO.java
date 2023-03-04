package com.endava.pokemonChallengue.models.dto;

import com.endava.pokemonChallengue.models.dto.ability.EffectDTO;
import com.endava.pokemonChallengue.models.dto.ability.NameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbilityDTO {
    private List<NameDTO> names;
    private List<EffectDTO> effect_entries;
}
