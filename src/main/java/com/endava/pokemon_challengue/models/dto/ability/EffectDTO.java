package com.endava.pokemon_challengue.models.dto.ability;

import com.endava.pokemon_challengue.models.dto.language.LanguageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EffectDTO {
    private String effect;
    private LanguageDTO language;
}
