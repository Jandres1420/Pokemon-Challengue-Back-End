package com.endava.pokemon_challengue.models.dto.ability;

import com.endava.pokemon_challengue.models.dto.language.LanguageDTO;
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