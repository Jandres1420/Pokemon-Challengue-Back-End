package com.endava.pokemon_challengue.exceptions.response;

import lombok.Data;

@Data
public class UnauthorizedResponse {
    private String responseCode = "UNAUTHORIZED";
    private String responseMessage;
}
