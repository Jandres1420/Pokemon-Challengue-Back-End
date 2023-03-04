package com.endava.pokemonChallengue.models.dto.type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypesDTO {
    private int slot;
    private TypeDTO type;
}
