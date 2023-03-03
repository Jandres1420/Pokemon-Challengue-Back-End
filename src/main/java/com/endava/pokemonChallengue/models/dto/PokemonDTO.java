package com.endava.pokemonChallengue.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDTO {
    private String name;
    private int order;
    private List<Object> stats;
    private List<Object> abilities;
}
