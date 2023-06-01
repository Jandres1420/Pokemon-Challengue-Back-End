package com.endava.pokemon_challengue.models.dto.dashboard;

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
    private List<String> typesInLanguage;
    private String img_path;
}
