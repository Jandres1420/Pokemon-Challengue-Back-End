package com.endava.pokemonChallengue.models.dto.responseBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvolutionChainResponse {
    private String name;
    private String detailed_url;
}
