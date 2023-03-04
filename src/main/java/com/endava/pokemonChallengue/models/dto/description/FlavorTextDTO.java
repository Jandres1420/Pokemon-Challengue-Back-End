package com.endava.pokemonChallengue.models.dto.description;

import com.endava.pokemonChallengue.models.dto.language.LanguageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlavorTextDTO {
    private String flavor_text;
    private LanguageDTO language;
}
