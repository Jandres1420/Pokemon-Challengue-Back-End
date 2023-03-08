package com.endava.pokemon_challengue.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "following")
@Entity
@Table(name = "_user")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;


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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Capture> captures;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="User_Relationship",
            joinColumns={@JoinColumn(name="father")},
            inverseJoinColumns={@JoinColumn(name="child")})
    @JsonBackReference
    private Set<UserProfile> followers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    @JoinTable(name="User_Relationship",
            joinColumns={@JoinColumn(name="child")},
            inverseJoinColumns={@JoinColumn(name="father")})
     private Set<UserProfile> following = new HashSet<>();

    public void addFollower(UserProfile userProfile){
        this.following.add(userProfile);
    }

    public Set<UserProfile> getFollowing(){
        return this.following;
    }
}
