package com.endava.pokemonChallengue.models.dto.ability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbilityResponseDTO {
    private String name;
    private String description;
}
