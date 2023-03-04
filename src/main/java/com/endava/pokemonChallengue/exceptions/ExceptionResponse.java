package com.endava.pokemonChallengue.exceptions;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ExceptionResponse {

    private LocalDateTime timeStamp;
    private String message;

    public ExceptionResponse() {
        timeStamp = LocalDateTime.now();
    }
}
