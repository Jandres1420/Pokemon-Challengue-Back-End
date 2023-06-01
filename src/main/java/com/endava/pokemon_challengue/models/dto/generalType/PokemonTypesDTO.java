package com.endava.pokemon_challengue.models.dto.generalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonTypesDTO {

    private List<TypesNameDTO> names;
}
