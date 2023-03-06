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
@Table(name = "Description")
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long description_id;

    private String d_english;
    private String d_spanish;
    private String d_japanese;
    private String d_german;

    @OneToOne()
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;
}