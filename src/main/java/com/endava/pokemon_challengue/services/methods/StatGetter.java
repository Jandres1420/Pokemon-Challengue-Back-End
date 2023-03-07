package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Stat;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.stat.StatsDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class StatGetter {

    public Stat getStat(PokemonDTO pokemonDTO) {
        List<StatsDTO> stats = pokemonDTO.getStats();

        return Stat.builder()
                .health(stats.get(0).getBase_stat())
                .attack(stats.get(1).getBase_stat())
                .defense(stats.get(2).getBase_stat())
                .specialAttack(stats.get(3).getBase_stat())
                .specialDefense(stats.get(4).getBase_stat())
                .speed(stats.get(5).getBase_stat())
                .build();
    }

}
