package com.endava.pokemonChallengue.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne(mappedBy="pokemon", cascade = CascadeType.ALL)
    private Stat stat;

    @OneToOne(mappedBy="pokemon", cascade = CascadeType.ALL)
    private Description description;

}
