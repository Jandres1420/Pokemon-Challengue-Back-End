package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.Ability;
import com.endava.pokemonChallengue.models.Description;
import com.endava.pokemonChallengue.models.Pokemon;
import com.endava.pokemonChallengue.models.Stat;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.stat.StatsDTO;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.services.methods.AbilityGetter;
import com.endava.pokemonChallengue.services.methods.DescriptionGetter;
import com.endava.pokemonChallengue.services.methods.PokemonGetter;
import com.endava.pokemonChallengue.services.methods.StatGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonApiService {
    private final PokemonRepository pokemonRepository;

    public void pokemonService(PokemonDTO pokemonDTO, PokemonSpeciesDTO pokemonSpeciesDTO, List<AbilityDTO> abilitiesDTO) {
        PokemonGetter pokemonGetter = new PokemonGetter();
        DescriptionGetter descriptionGetter = new DescriptionGetter();
        AbilityGetter abilityGetter = new AbilityGetter();
        StatGetter statGetter = new StatGetter();

        Pokemon pokemon = pokemonGetter.getPokemon(pokemonDTO);
        Optional<Pokemon> foundPokemon = pokemonRepository.findPokemon(pokemon.getPokemon_id(),pokemon.getName());

        if(!foundPokemon.isPresent()) {
            Stat stat = statGetter.getStat(pokemonDTO);
            stat.setPokemon(pokemon);
            pokemon.setStat(stat);

            Description description = descriptionGetter.getDescription(pokemonSpeciesDTO);
            description.setPokemon(pokemon);
            pokemon.setDescription(description);

            pokemon.setAbilities(new ArrayList<>());

            for (int i = 0; i < abilitiesDTO.size(); i++) {
                Ability ability = abilityGetter.getAbility(abilitiesDTO.get(i));
                pokemon.getAbilities().add(ability);
            }
            pokemonRepository.save(pokemon);
        }
    }
}
