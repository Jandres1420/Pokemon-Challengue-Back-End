package com.endava.pokemonChallengue.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pokemon")
public class Pokemon {

    @Id
    private int pokemon_id;

    private String name;
    private String type;
    private String description;
    private String img_path;

}
