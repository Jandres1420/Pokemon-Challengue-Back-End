package com.endava.pokemon_challengue.services;

import com.endava.pokemon_challengue.models.*;
import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.IndividualPokemonFromTrainerDto;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.repositories.CaptureRepository;
import com.endava.pokemon_challengue.repositories.UserProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Captor
    ArgumentCaptor<UserProfile> userProfileArgumentCaptor;

    @Captor
    ArgumentCaptor<Capture> captureArgumentCaptor;
    @Mock
    UserProfileRepository userProfileRepository;
    @Mock
    CaptureRepository captureRepository;

    @InjectMocks
    RoleService roleService;

    @InjectMocks
    AuthService authService;

    @Test
    void seePokemonFromTrainer() {
        Pokemon pokemon = Pokemon.builder().pokemon_id(63).name("Abra").type("psychic")
                .stat(Stat.builder().attack(20).defense(40).specialAttack(60).specialDefense(10)
                        .build()).build();
        Pokemon pokemon2 = Pokemon.builder().pokemon_id(25).name("pikachu").type("electric")
                .stat(Stat.builder().attack(35).defense(34).specialAttack(60).specialDefense(10)
                        .build()).build();
        List<Capture> captureList = List.of(Capture.builder().pokemon(pokemon).build(),
                Capture.builder().nickname("pikachu andres").pokemon(pokemon2).build());

        UserProfile connected = UserProfile.builder().role(Role.ADMIN).connect(true).password("andres")
                .email("andres@endava.com").captures(captureList).username("andres").build();
        UserProfile userAsking = UserProfile.builder().role(Role.TRAINER).connect(true).password("gabriel")
                .captures(captureList).email("gabriel@endava.com").captures(captureList).username("gabriel").build();
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(userAsking));
        when(userProfileRepository.findByUsername("gabriel")).thenReturn(Optional.of(connected));
        roleService.seePokemonFromTrainer(connected.getUsername(),0,0,userAsking.getUsername(),"","");
    }


    @Test
    void Given_CaptureId_When_CurePokemonDoctor_Then_CurePokemon() {
        Pokemon pokemon = Pokemon.builder().pokemon_id(63).name("Abra").type("psychic")
                .stat(Stat.builder().attack(20).defense(40).health(100).specialAttack(60).specialDefense(10)
                        .build()).build();

        List<Capture> captureList = List.of(Capture.builder().capture_id(1L).pokemon(pokemon).health_status(0).nickname("abra andres")
                        .build());
        UserProfile connected = UserProfile.builder().role(Role.DOCTOR).connect(true).password("gabriel").email("gabriel@endava.com").captures(captureList).username("gabriel").build();
        Capture capture = Capture.builder().capture_id(1L).user(connected).pokemon(pokemon).build();
        when(userProfileRepository.findByUsername("gabriel")).thenReturn(Optional.of(connected));
        when(captureRepository.save(capture)).thenReturn(capture);
        when(captureRepository.findCaptureByCaptureId(1L)).thenReturn(Optional.of(capture));
        GeneralResponse generalResponse =  roleService.curePokemonDoctor(1L,connected.getUsername());
        verify(captureRepository).save(captureArgumentCaptor.capture());
        assertEquals("You have cured the Abra of gabriel",generalResponse.getResponseMessage());
    }

    @Test
    void Given_TwoUsersFollow_When_FollowAndUnfollowTrainer_Then_ConnectedFollowsTrainerToFollow() {
        Set<UserProfile> following = new HashSet<>();
        UserProfile connected = UserProfile.builder().role(Role.TRAINER).connect(true).password("gabriel").email("gabriel@endava.com").following(following).username("gabriel").build();
        UserProfile trainerToFollow = UserProfile.builder().role(Role.TRAINER).connect(true).password("andres").username("andres").email("andres@endava.com").build();
        when(userProfileRepository.save(connected)).thenReturn(connected);
        when(userProfileRepository.findByUsername("gabriel")).thenReturn(Optional.of(connected));
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(trainerToFollow));
        roleService.followAndUnfollowTrainer(trainerToFollow.getUsername(), FollowRequest.builder().action("follow").build(),connected.getUsername());
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(userProfileArgumentCaptor.getValue().getFollowing().size(),1);
    }

    @Test
    void Given_TwoUsersUnfollow_When_FollowAndUnfollowTrainer_Then_ConnectedFollowsTrainerToFollow() {
        Set<UserProfile> following = new HashSet<>();
        UserProfile trainerToFollow = UserProfile.builder().role(Role.TRAINER).connect(true).password("andres").username("andres").email("andres@endava.com").build();
        following.add(trainerToFollow);
        UserProfile connected = UserProfile.builder().role(Role.TRAINER).connect(true).password("gabriel").email("gabriel@endava.com").following(following).username("gabriel").build();
        when(userProfileRepository.save(connected)).thenReturn(connected);
        when(userProfileRepository.findByUsername("gabriel")).thenReturn(Optional.of(connected));
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(trainerToFollow));
        roleService.followAndUnfollowTrainer(trainerToFollow.getUsername(), FollowRequest.builder().action("unfollow").build(),connected.getUsername());
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(userProfileArgumentCaptor.getValue().getFollowing().size(),0);
    }

    @Test
    void Given_AdminRole_When_AdministrateProfiles_Then_GivePersonOakRole() {
        UserProfile admin = UserProfile.builder().role(Role.ADMIN).connect(true).password("admin").email("admin@endava.com").username("admin").build();
        UserProfile trainer = UserProfile.builder().role(Role.TRAINER).connect(true).password("andres").username("andres").email("andres@endava.com").build();
        when(userProfileRepository.save(trainer)).thenReturn(trainer);
        when(userProfileRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(trainer));
        roleService.administrateProfiles(AdminRoleChange.builder().username("andres").role("professor").build(), admin.getUsername());
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(Role.OAK,userProfileArgumentCaptor.getValue().getRole());
    }

    @Test
    void Given_AdminRole_When_AdministrateProfiles_Then_GivePersonDoctorRole() {
        UserProfile admin = UserProfile.builder().role(Role.ADMIN).connect(true).password("admin").email("admin@endava.com").username("admin").build();
        UserProfile trainer = UserProfile.builder().role(Role.TRAINER).connect(true).password("andres").username("andres").email("andres@endava.com").build();
        when(userProfileRepository.save(trainer)).thenReturn(trainer);
        when(userProfileRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(trainer));
        roleService.administrateProfiles(AdminRoleChange.builder().username("andres").role("doctor").build(), admin.getUsername());
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(Role.DOCTOR,userProfileArgumentCaptor.getValue().getRole());
    }

    @Test
    void Given_AdminRole_When_AdministrateProfiles_Then_GivePersonAdminRole() {
        UserProfile admin = UserProfile.builder().role(Role.ADMIN).connect(true).password("admin").email("admin@endava.com").username("admin").build();
        UserProfile trainer = UserProfile.builder().role(Role.TRAINER).connect(true).password("andres").username("andres").email("andres@endava.com").build();
        when(userProfileRepository.save(trainer)).thenReturn(trainer);
        when(userProfileRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(trainer));
        roleService.administrateProfiles(AdminRoleChange.builder().username("andres").role("admin").build(), admin.getUsername());
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(Role.ADMIN,userProfileArgumentCaptor.getValue().getRole());
    }

    @Test
    void Given_AdminRole_When_AdministrateProfiles_Then_GivePersonTrainerRole() {
        UserProfile admin = UserProfile.builder().role(Role.ADMIN).connect(true).password("admin").email("admin@endava.com").username("admin").build();
        UserProfile trainer = UserProfile.builder().role(Role.DOCTOR).connect(true).password("andres").username("andres").email("andres@endava.com").build();
        when(userProfileRepository.save(trainer)).thenReturn(trainer);
        when(userProfileRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(userProfileRepository.findByUsername("andres")).thenReturn(Optional.of(trainer));
        roleService.administrateProfiles(AdminRoleChange.builder().username("andres").role("trainer").build(), admin.getUsername());
        verify(userProfileRepository).save(userProfileArgumentCaptor.capture());
        Assertions.assertEquals(Role.TRAINER,userProfileArgumentCaptor.getValue().getRole());
    }


    @Test
    void userExist() {
    }

    @Test
    void Given_CollectionOfInDividualPokemonFromTrainerDto_When_SortBy_Then_GiveMeTheCollectionSortedAlphabetical() {
        String sortBy = "name";
        IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto = IndividualPokemonFromTrainerDto.builder()
                .nickname("Mew Andres")
                .name("Mew")
                .id(151)
                .build();
        IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto2 = IndividualPokemonFromTrainerDto.builder()
                .nickname("arbok Andres")
                .name("Arbok")
                .id(24)
                .build();
        IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto3 = IndividualPokemonFromTrainerDto.builder()
                .nickname("Lucario Andres")
                .name("Lucario")
                .id(448)
                .build();

        List<IndividualPokemonFromTrainerDto> individualPokemonFromTrainerDtoList =
                List.of(individualPokemonFromTrainerDto,individualPokemonFromTrainerDto2,individualPokemonFromTrainerDto3);

        Collection<IndividualPokemonFromTrainerDto> sorted =  roleService.sortBy(sortBy,individualPokemonFromTrainerDtoList);
        Assertions.assertNotEquals(sorted, individualPokemonFromTrainerDtoList);
    }

    @Test
    void Given_CollectionOfInDividualPokemonFromTrainerDto_When_SortBy_Then_GiveMeTheCollectionSortedById() {
        String sortBy = "id";
        IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto = IndividualPokemonFromTrainerDto.builder()
                .nickname("Mew Andres")
                .name("Mew")
                .id(151)
                .build();
        IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto2 = IndividualPokemonFromTrainerDto.builder()
                .nickname("arbok Andres")
                .name("Arbok")
                .id(24)
                .build();
        IndividualPokemonFromTrainerDto individualPokemonFromTrainerDto3 = IndividualPokemonFromTrainerDto.builder()
                .nickname("Lucario Andres")
                .name("Lucario")
                .id(448)
                .build();

        List<IndividualPokemonFromTrainerDto> individualPokemonFromTrainerDtoList =
                List.of(individualPokemonFromTrainerDto,individualPokemonFromTrainerDto2,individualPokemonFromTrainerDto3);

        Collection<IndividualPokemonFromTrainerDto> sorted =  roleService.sortBy(sortBy,individualPokemonFromTrainerDtoList);
        Assertions.assertNotEquals(sorted, individualPokemonFromTrainerDtoList);
    }
}