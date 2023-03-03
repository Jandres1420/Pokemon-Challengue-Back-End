package com.endava.pokemonChallengue.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonSpeciesDTO {
    private Object evolution_chain;
    private List<Object> flavor_text_entries;
}
