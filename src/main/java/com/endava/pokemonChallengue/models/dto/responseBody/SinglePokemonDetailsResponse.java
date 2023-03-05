package com.endava.pokemonChallengue.models.dto.responseBody;

import com.endava.pokemonChallengue.models.Ability;
import com.endava.pokemonChallengue.models.Stat;
import com.endava.pokemonChallengue.models.dto.ability.AbilityResponseDTO;
import com.endava.pokemonChallengue.models.dto.stat.StatResponseDTO;
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
    private List<String> type;
    private String language;
    private String img_path;
    private String description;
    private StatResponseDTO stats;
    private List<AbilityResponseDTO> abilities;
}
