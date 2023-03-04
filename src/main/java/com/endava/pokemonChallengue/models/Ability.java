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
@Table(name = "Ability")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ability_id;

    private String n_english;
    private String n_spanish;
    private String n_japanese;
    private String n_french;
    private String d_english;
    private String d_spanish;
    private String d_japanese;
    private String d_french;

    /*
    @OneToOne()
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;*/
}
