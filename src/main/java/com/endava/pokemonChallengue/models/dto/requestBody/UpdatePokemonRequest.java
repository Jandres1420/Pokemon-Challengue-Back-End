package com.endava.pokemonChallengue.models.dto.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePokemonRequest {
    private Long id;
    private String nickname;
}
