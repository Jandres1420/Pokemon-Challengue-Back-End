package com.endava.pokemonChallengue.models.dto.responseBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvolutionResponse {
    private List<EvolutionChainResponse> evolution_chain;
    private List<EvolutionChainResponse> next_evolution;
}
