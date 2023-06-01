package com.endava.pokemon_challengue.models.dto.responseBody;

import com.endava.pokemon_challengue.models.dto.dashboard.PokemonResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsResponseDTO {
    private int quantity;
    private List<SinglePokemonDetailsResponse> results;
}
