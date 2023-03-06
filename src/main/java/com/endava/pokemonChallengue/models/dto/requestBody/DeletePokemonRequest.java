package com.endava.pokemonChallengue.models.dto.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePokemonRequest {
    private Long id;
}
