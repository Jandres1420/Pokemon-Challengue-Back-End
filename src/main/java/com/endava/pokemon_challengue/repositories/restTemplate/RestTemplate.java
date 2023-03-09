package com.endava.pokemon_challengue.repositories.restTemplate;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class RestTemplate {
    private final org.springframework.web.client.RestTemplate restExternalApi;
}
