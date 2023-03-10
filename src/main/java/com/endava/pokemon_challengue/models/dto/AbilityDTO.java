package com.endava.pokemon_challengue.models.dto;

import com.endava.pokemon_challengue.models.dto.ability.EffectDTO;
import com.endava.pokemon_challengue.models.dto.ability.NameDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbilityDTO {
    private int id;
    private List<NameDTO> names;
    private List<EffectDTO> effect_entries;
}
