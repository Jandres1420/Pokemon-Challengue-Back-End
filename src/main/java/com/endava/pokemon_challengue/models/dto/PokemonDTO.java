package com.endava.pokemon_challengue.models.dto;


import com.endava.pokemon_challengue.models.dto.abilityUrl.AbilitiesUrlDTO;
import com.endava.pokemon_challengue.models.dto.image.SpritesDTO;
import com.endava.pokemon_challengue.models.dto.stat.StatsDTO;
import com.endava.pokemon_challengue.models.dto.type.TypesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
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
