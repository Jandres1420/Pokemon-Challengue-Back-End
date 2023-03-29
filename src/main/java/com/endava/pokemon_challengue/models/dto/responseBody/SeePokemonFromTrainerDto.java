package com.endava.pokemon_challengue.models.dto.responseBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeePokemonFromTrainerDto {
    private String username;
    private int quantity;
    private int index;

    private String language;
    private Collection<IndividualPokemonFromTrainerDto> result;
}
