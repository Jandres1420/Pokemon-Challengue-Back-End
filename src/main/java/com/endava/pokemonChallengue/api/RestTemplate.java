package com.endava.pokemonChallengue.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class RestTemplate {

    private final org.springframework.web.client.RestTemplate restTemplate;

    public Object getResponseObject(String url){
        Object responseObject = restTemplate.getForObject(url, Object.class);
        return responseObject;
    }

}
