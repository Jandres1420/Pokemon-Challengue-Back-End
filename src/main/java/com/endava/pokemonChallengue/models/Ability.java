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
    private int ability_id;

    private String n_english;
    private String n_spanish;
    private String n_japanese;
    private String n_german;

    @Column(name = "d_english",nullable = false,length = 1000)
    private String d_english;
    @Column(name = "d_spanish",nullable = false,length = 1000)
    private String d_spanish;
    @Column(name = "d_japanese",nullable = false,length = 1000)
    private String d_japanese;
    @Column(name = "d_german",nullable = false,length = 1000)
    private String d_german;

    /*
    @OneToOne()
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;*/
}
