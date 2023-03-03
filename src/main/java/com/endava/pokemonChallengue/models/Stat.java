package com.endava.pokemonChallengue.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stat")

public class Stat {

    @Id
    @SequenceGenerator(name = "stat_sequence", sequenceName = "stat_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stat_sequence")
    private Long stat_id;

    private int health;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    @OneToOne()
    @JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id")
    private Pokemon pokemon;

    public Stat(int health, int attack, int defense, int specialAttack, int specialDefense, int speed, Pokemon pokemon) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.pokemon = pokemon;
    }
}
