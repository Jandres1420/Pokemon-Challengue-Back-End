package com.endava.pokemon_challengue.models.dto;

import com.endava.pokemon_challengue.models.dto.evolution.ChainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvolutionDTO {
    private ChainDTO chain;
}
