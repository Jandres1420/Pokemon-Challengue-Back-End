package com.endava.pokemon_challengue.models;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(targetEntity = UserProfile.class)
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @ManyToOne(targetEntity = Pokemon.class)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

}