package com.endava.pokemon_challengue.models.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultsDTO {
    private List<ResultDTO> results;
}

