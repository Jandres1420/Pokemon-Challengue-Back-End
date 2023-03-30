package com.endava.pokemon_challengue.models.dto.responseBody;

import com.endava.pokemon_challengue.models.dto.ability.AbilityResponseDTO;
import com.endava.pokemon_challengue.models.dto.stat.StatResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SinglePokemonDetailsResponse {
    private String name;
    private int id;
    private List<String> types;
    private String language;
    private String img_path;
    private String description;
    private StatResponseDTO stats;

    private List<String> typesInLanguage;
    private List<AbilityResponseDTO> abilities;
}
