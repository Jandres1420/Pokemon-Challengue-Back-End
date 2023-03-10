package com.endava.pokemon_challengue.models.dto.description;

import com.endava.pokemon_challengue.models.dto.language.LanguageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlavorTextDTO {
    private String flavor_text;
    private LanguageDTO language;
}
