package com.endava.pokemonChallengue.models.dto.ability;

import com.endava.pokemonChallengue.models.dto.language.LanguageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameDTO {
    private String name;
    private LanguageDTO language;
}
