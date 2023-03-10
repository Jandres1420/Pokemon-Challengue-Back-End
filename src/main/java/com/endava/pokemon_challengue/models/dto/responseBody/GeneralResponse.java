package com.endava.pokemon_challengue.models.dto.responseBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    private String responseCode;
    private String responseMessage;

}
