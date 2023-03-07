package com.endava.pokemon_challengue.models.dto;

import com.endava.pokemon_challengue.models.dto.description.FlavorTextDTO;
import com.endava.pokemon_challengue.models.dto.evolution.EvolutionUrlDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonSpeciesDTO {
    private EvolutionUrlDTO evolution_chain;
    private List<FlavorTextDTO> flavor_text_entries;
}
