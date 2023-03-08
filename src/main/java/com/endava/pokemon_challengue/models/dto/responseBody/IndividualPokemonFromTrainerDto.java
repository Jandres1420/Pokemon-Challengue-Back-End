package com.endava.pokemon_challengue.models.dto.responseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualPokemonFromTrainerDto {
    private String name;
    private int id;
    private List<String> type;
}