package com.endava.pokemonChallengue.services.methods;

import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class PokemonGetter {

    public Pokemon getPokemon(PokemonDTO pokemonDTO, PokemonSpeciesDTO pokemonSpeciesDTO) {
        String types = "";
        for (int i = 0; i < pokemonDTO.getTypes().size(); i++) {
            types += pokemonDTO.getTypes().get(i).getType().getName() + ", ";
        }
        pokemonDTO.setTypeString(types);

        String imagePath = pokemonDTO
                .getSprites()
                .getOther()
                .getDream_world()
                .getFront_default();

        return Pokemon.builder()
                .pokemon_id(pokemonDTO.getId())
                .name(pokemonDTO.getName())
                .type(types)
                .img_path(imagePath)
                .evolution_url(pokemonSpeciesDTO.getEvolution_chain().getUrl())
                .build();
    }
}
