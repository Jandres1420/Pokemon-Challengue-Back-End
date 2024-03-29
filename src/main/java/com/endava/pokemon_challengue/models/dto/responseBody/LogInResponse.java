package com.endava.pokemon_challengue.models.dto.responseBody;

import com.endava.pokemon_challengue.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInResponse {
    private int id;
    private String email;
    private String username;
    private String name;
    private String last_name;
    private String password;
    private Role role;
}
