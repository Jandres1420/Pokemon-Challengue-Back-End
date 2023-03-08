package com.endava.pokemon_challengue.models.dto.requestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
    private String email;
    private String username;
    private String password;
    private String name;
    private String LastName;
}
