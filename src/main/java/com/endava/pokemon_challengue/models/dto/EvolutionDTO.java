package com.endava.pokemon_challengue.models.dto;

import com.endava.pokemon_challengue.models.dto.evolution.ChainDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvolutionDTO {
    private ChainDTO chain;
}
