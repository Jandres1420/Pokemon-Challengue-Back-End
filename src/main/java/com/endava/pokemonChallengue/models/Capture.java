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
@Table(name = "Capture")
public class Capture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long capture_id;

    private String username;
    private int pokemon_id;
    private String nickname;
    private int health_status;


}