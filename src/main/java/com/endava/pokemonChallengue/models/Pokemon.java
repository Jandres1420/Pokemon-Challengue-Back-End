package com.endava.pokemonChallengue.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Pokemon")
public class Pokemon {
    @Id
    private int pokemon_id;

    private String name;
    private String type;
    private String img_path;
    private String evolution_url;

    @OneToOne(mappedBy="pokemon", cascade = CascadeType.ALL)
    private Stat stat;

    @OneToOne(mappedBy="pokemon", cascade = CascadeType.ALL)
    private Description description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Pokemon_Ability",
            joinColumns = {@JoinColumn(name="pokemon_id")},
            inverseJoinColumns = {@JoinColumn (name = "ability_id")})
    private List<Ability> abilities;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL)
    private List<Capture> captures;

}