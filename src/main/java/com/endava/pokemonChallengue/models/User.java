package com.endava.pokemonChallengue.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @NotEmpty
    @NotNull
    private String username;
    private String name;
    private String lastName;
    @NotEmpty
    @NotNull
    private String email;
    @NotEmpty
    @NotNull
    private String password;
    private Role role;
    private Boolean connect;
}
