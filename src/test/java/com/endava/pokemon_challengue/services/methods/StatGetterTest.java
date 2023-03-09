package com.endava.pokemon_challengue.services.methods;

import com.endava.pokemon_challengue.models.Stat;
import com.endava.pokemon_challengue.models.dto.PokemonDTO;
import com.endava.pokemon_challengue.models.dto.stat.StatDTO;
import com.endava.pokemon_challengue.models.dto.stat.StatsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatGetterTest {

    @InjectMocks
    StatGetter statGetter;

    @Test
    void getStat() {
        List<StatsDTO> stats = new ArrayList<>();

        stats.add(StatsDTO.builder().base_stat(1).stat(StatDTO.builder().name("health").build()).build());
        stats.add(StatsDTO.builder().base_stat(2).stat(StatDTO.builder().name("attack").build()).build());
        stats.add(StatsDTO.builder().base_stat(3).stat(StatDTO.builder().name("defense").build()).build());
        stats.add(StatsDTO.builder().base_stat(4).stat(StatDTO.builder().name("specialAttack").build()).build());
        stats.add(StatsDTO.builder().base_stat(5).stat(StatDTO.builder().name("specialDefense").build()).build());
        stats.add(StatsDTO.builder().base_stat(6).stat(StatDTO.builder().name("Speed").build()).build());


        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .stats(stats)
                .build();

        Stat stat = statGetter.getStat(pokemonDTO);

        Assertions.assertEquals(stat.getHealth(),1);
        Assertions.assertEquals(stat.getAttack(),2);
        Assertions.assertEquals(stat.getDefense(),3);
        Assertions.assertEquals(stat.getSpecialAttack(),4);
        Assertions.assertEquals(stat.getSpecialDefense(),5);
        Assertions.assertEquals(stat.getSpeed(),6);

    }
}