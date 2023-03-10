package com.endava.pokemon_challengue.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Ability")
public class Ability {
    @Id
    private int ability_id;

    private String n_english;
    private String n_spanish;
    private String n_japanese;
    private String n_german;

    @Column(name = "d_english", nullable = false, length = 2000)
    private String d_english;
    @Column(name = "d_spanish", nullable = false, length = 2000)
    private String d_spanish;
    @Column(name = "d_japanese", nullable = false, length = 2000)
    private String d_japanese;
    @Column(name = "d_german", nullable = false, length = 2000)
    private String d_german;

    @ManyToMany(mappedBy = "abilities")
    private List<Pokemon> pokemons;
}
