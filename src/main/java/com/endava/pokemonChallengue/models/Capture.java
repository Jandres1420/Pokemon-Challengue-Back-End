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

    private String nickname;
    private int health_status;

    @ManyToOne(targetEntity = UserInfo.class,  cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @ManyToOne(targetEntity = Pokemon.class,  cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;


}