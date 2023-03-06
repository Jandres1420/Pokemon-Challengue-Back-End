package com.endava.pokemonChallengue.models.dto;

import com.endava.pokemonChallengue.models.dto.description.FlavorTextDTO;
import com.endava.pokemonChallengue.models.dto.evolution.ChainDTO;
import com.endava.pokemonChallengue.models.dto.evolution.EvolutionUrlDTO;
import com.endava.pokemonChallengue.models.dto.evolution.SpeciesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvolutionDTO {
    private ChainDTO chain;
}
