package com.endava.pokemonChallengue.models.dto.dashboard;

import com.endava.pokemonChallengue.models.dto.type.TypesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonResponseDTO {
    private String name;
    private int id;
    private List<String> types;
    private String img_path;
}
