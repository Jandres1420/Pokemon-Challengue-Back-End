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
public class SeePokemonFromTrainerDto {
    private int quantity;
    private int index;
    private List<ResultSeePokemonFromTrainerDto> result;
}