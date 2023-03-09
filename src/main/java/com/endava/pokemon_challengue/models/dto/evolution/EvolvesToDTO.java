package com.endava.pokemon_challengue.models.dto.evolution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvolvesToDTO {
    private List<EvolvesToDTO> evolves_to;
    private SpeciesDTO species;
}