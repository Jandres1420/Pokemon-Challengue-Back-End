package com.endava.pokemon_challengue.models.dto.abilityUrl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbilityUrlDTO {
    private String name;
    private String url;
}
