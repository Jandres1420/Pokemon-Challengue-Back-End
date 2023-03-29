package com.endava.pokemon_challengue.controllers;

import com.endava.pokemon_challengue.models.dto.requestBody.AdminRoleChange;
import com.endava.pokemon_challengue.models.dto.requestBody.FollowRequest;
import com.endava.pokemon_challengue.models.dto.responseBody.GeneralResponse;
import com.endava.pokemon_challengue.models.dto.responseBody.SeePokemonFromTrainerDto;
import com.endava.pokemon_challengue.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    RoleService roleServiceMock;

    @InjectMocks
    RoleController roleController;

    @Test
    void Given_AllArguments_When_SeePokemonFromTrainer_Then_Success() {

        when(roleServiceMock.seePokemonFromTrainer("gabriel", 1, 0, "gabriel", "", ""))
                .thenReturn(SeePokemonFromTrainerDto.builder().username("gabriel").build());

        SeePokemonFromTrainerDto seePokemonFromTrainerDto = roleController.seePokemonsFromTrainer("gabriel", 1, 0, "gabriel", "", "","");

        Assertions.assertEquals(seePokemonFromTrainerDto.getUsername(),"gabriel");
    }

    @Test
    void Given_AllArguments_When_CurePokemonDoctor_Then_Success() {
        long captureId = 5;
        when(roleServiceMock.curePokemonDoctor(captureId, "gabriel"))
                .thenReturn(GeneralResponse.builder().responseCode("Ok").responseMessage("Done").build());

        GeneralResponse generalResponse = roleController.curePokemonDoctor(captureId, "gabriel");

        Assertions.assertEquals(generalResponse.getResponseCode(),"Ok");
        Assertions.assertEquals(generalResponse.getResponseMessage(),"Done");
    }

    @Test
    void Given_AllArguments_When_FollowUnfollow_Then_Success() {
        long captureId = 5;
        when(roleServiceMock.followAndUnfollowTrainer("gabriel",
                FollowRequest.builder().action("follow").build(),
                "andres"))
                .thenReturn(GeneralResponse.builder().responseCode("Ok").responseMessage("Done").build());

        GeneralResponse generalResponse = roleController.followAndUnfollowTrainer(
                "gabriel",
                FollowRequest.builder().action("follow").build(),
                "andres");

        Assertions.assertEquals(generalResponse.getResponseCode(),"Ok");
        Assertions.assertEquals(generalResponse.getResponseMessage(),"Done");
    }

    @Test
    void Given_AllArguments_When_AdministrateProfile_Then_Success() {
        when(roleServiceMock.administrateProfiles(AdminRoleChange.builder().role("admin").build(), "admin"))
                .thenReturn(GeneralResponse.builder().responseCode("Ok").responseMessage("Done").build());

        GeneralResponse generalResponse = roleController.administrateProfiles(
                AdminRoleChange.builder().role("admin").build(), "admin");

        Assertions.assertEquals(generalResponse.getResponseCode(),"Ok");
        Assertions.assertEquals(generalResponse.getResponseMessage(),"Done");
    }
}