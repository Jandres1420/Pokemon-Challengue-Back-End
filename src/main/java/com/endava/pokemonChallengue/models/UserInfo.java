package com.endava.pokemonChallengue.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;



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
public class UserInfo {
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
//        @Getter(AccessLevel.PRIVATE)
//    @Setter(AccessLevel.PRIVATE)
    private Set<UserInfo> followers = new HashSet<UserInfo>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    @JoinTable(name="User_Relationship",
            joinColumns={@JoinColumn(name="child")},
            inverseJoinColumns={@JoinColumn(name="father")})
//        @Getter(AccessLevel.PRIVATE)
//    @Setter(AccessLevel.PRIVATE)
     private Set<UserInfo> following = new HashSet<UserInfo>();

    public void addFollower(UserInfo userInfo){
        this.following.add(userInfo);
    }

    public Set<UserInfo> getFollowing(){
        return this.following;
    }
}
