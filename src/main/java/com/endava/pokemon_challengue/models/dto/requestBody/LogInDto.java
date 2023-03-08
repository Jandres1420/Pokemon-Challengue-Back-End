package com.endava.pokemon_challengue.models.dto.requestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogInDto {
    private String email;
    private String password;
}
