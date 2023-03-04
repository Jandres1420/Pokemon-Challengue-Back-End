package com.endava.pokemonChallengue.models.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInDto {
    private Long id;
    private String email;
    private String username;


}
