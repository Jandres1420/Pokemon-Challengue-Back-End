package com.endava.pokemonChallengue.models.dto.evolution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChainDTO {
    private SpeciesDTO species;
    private List<EvolvesToDTO> evolves_to;
}