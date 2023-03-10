package com.endava.pokemon_challengue.models.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatResponseDTO {
    private int health;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
}
