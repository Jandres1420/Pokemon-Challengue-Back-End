package com.endava.pokemonChallengue.models.dto;


import com.endava.pokemonChallengue.models.dto.abilityUrl.AbilitiesUrlDTO;
import com.endava.pokemonChallengue.models.dto.image.SpritesDTO;
import com.endava.pokemonChallengue.models.dto.stat.StatsDTO;
import com.endava.pokemonChallengue.models.dto.type.TypesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDTO {
    private int id;
    private String name;
    private List<TypesDTO> types;
    private List<StatsDTO> stats;
    private List<AbilitiesUrlDTO> abilities;
    private String typeString;
    private SpritesDTO sprites;
}
