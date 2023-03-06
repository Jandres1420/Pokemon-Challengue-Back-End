package com.endava.pokemonChallengue.services;

import com.endava.pokemonChallengue.models.*;
import com.endava.pokemonChallengue.models.dto.AbilityDTO;
import com.endava.pokemonChallengue.models.dto.PokemonDTO;
import com.endava.pokemonChallengue.models.dto.PokemonSpeciesDTO;
import com.endava.pokemonChallengue.models.dto.responseBody.AddPokemonForm;
import com.endava.pokemonChallengue.models.dto.stat.StatsDTO;
import com.endava.pokemonChallengue.repositories.CaptureRepository;
import com.endava.pokemonChallengue.repositories.PokemonRepository;
import com.endava.pokemonChallengue.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final CaptureRepository captureRepository;

    public AddPokemonForm pokemonCapture(String username,
                                         String pokemonName,
                                         int pokemonId,
                                         String pokemonNickname,
                                         PokemonDTO pokemonDTO,
                                         PokemonSpeciesDTO pokemonSpeciesDTO,
                                         List<AbilityDTO> abilitiesDTO) {

        PokemonGetter pokemonGetter = new PokemonGetter();
        DescriptionGetter descriptionGetter = new DescriptionGetter();
        AbilityGetter abilityGetter = new AbilityGetter();
        StatGetter statGetter = new StatGetter();

        if(!pokemonRepository.findPokemon(pokemonId, pokemonName).isPresent()) {
            System.out.println("Pokemon nuevo");

            Pokemon pokemon = pokemonGetter.getPokemon(pokemonDTO, pokemonSpeciesDTO);

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

        }else{
            System.out.println("Pokemon encontrado en la base de datos");
        }

        Capture capture = Capture.builder()
                .pokemon(pokemonRepository.findPokemon(pokemonId, pokemonName).get())
                .user(userRepository.findByUsername(username).get())
                .health_status(pokemonDTO.getStats().get(0).getBase_stat())
                .nickname(pokemonNickname)
                .build();

        captureRepository.save(capture);

        return AddPokemonForm.builder()
                .responseCode("Ok")
                .responseMessage("Pokemon "+pokemonName+" added to "+ username)
                .build();
    }
}