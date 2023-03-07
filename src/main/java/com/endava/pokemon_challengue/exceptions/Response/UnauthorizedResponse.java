package com.endava.pokemon_challengue.exceptions.Response;

import lombok.Data;

@Data
public class UnauthorizedResponse {
    private String responseCode = "Unauthorized";
    private String responseMessage;
}
