package com.endava.pokemon_challengue.models.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {
    private int base_stat;
    private StatDTO stat;
}
