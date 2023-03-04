package com.endava.pokemonChallengue.models.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {
    private Long id;
    private String email;
    private String username;
}
