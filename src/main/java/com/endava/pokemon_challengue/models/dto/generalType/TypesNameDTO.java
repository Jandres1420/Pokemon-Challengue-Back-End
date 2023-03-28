package com.endava.pokemon_challengue.models.dto.generalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypesNameDTO {

    private String name;
    private LanguageType language;
}
