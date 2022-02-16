package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.ActorDTO;
import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.domain.exceptions.GenderException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.domain.services.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(ActorController.class)
public class ActorControllerTest {

    @MockBean
    private ActorService actorService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<ActorDTO>> jsonActorDTOList;

    @Autowired
    private JacksonTester<ActorDTO> jsonActorDTO;

    private ActorDTO actorDTO;

    private ActorEntity actor;

    private List<ActorDTO> actorsDTO;

    private List<ActorEntity> actors;

    @BeforeEach
    void setUp() {
        this.actor = new ActorEntity(1,"Pol","Farreny","Spain",24,GenderType.MALE,null);
        this.actorDTO = new ActorDTO(this.actor);
        this.actors = new ArrayList<>();
        this.actors.add(this.actor);
        this.actorsDTO = new ArrayList<>();
        this.actorsDTO.add(this.actorDTO);
    }

    @Test
    void testGivenSavedActorsWhenGetAllActorsThenReturnOk() throws Exception {
        int page = 0;
        int size = 1;
        given(this.actorService.getAllActors(PageRequest.of(page,size))).willReturn(this.actors);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTOList.write(this.actorsDTO).getJson()));
    }

    @Test
    void testGivenNonSavedActorsWhenGetAllActorsThenReturnNotFound() throws Exception {
        int page = 0;
        int size = 1;
        given(this.actorService.getAllActors(PageRequest.of(page,size))).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedActorsWhenGetActorsByAgeThenReturnOk() throws Exception {
        int age = 24;
        given(this.actorService.getActorsByAge(age)).willReturn(this.actors);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.AGE, age)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTOList.write(this.actorsDTO).getJson()));
    }

    @Test
    void testGivenNonSavedActorsWhenGetActorsByAgeThenReturnNotFound() throws Exception {
        int age = 24;
        given(this.actorService.getActorsByAge(age)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.AGE, age)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedActorsWhenGetActorsByCountryThenReturnOk() throws Exception {
        String country = "Spain";
        given(this.actorService.getActorsByCountry(country)).willReturn(this.actors);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.COUNTRY, country)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTOList.write(this.actorsDTO).getJson()));
    }

    @Test
    void testGivenNonSavedActorsWhenGetActorsByCountryThenReturnNotFound() throws Exception {
        String country = "Spain";
        given(this.actorService.getActorsByCountry(country)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.COUNTRY, country)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedActorsWhenGetActorsByCorrectGenderThenReturnOk() throws Exception {
        String gender = "MALE";
        given(this.actorService.getActorsByGender(gender)).willReturn(this.actors);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.GENDER, gender)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTOList.write(this.actorsDTO).getJson()));
    }

    @Test
    void testGivenSavedActorsWhenGetActorsByIncorrectGenderThenReturnBadRequest() throws Exception {
        String gender = "NULL";
        given(this.actorService.getActorsByGender(gender)).willThrow(GenderException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.GENDER, gender)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenNonSavedActorsWhenGetActorsByCorrectGenderThenReturnNotFound() throws Exception {
        String gender = "FEMALE";
        given(this.actorService.getActorsByGender(gender)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.GENDER, gender)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenExistentActorWhenGetActorByNameAndSurnameThenReturnOk() throws Exception {
        String name = "Pol";
        String surname = "Farreny";
        given(this.actorService.getActorByNameAndSurname(name, surname)).willReturn(this.actor);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.NAME + ActorController.SURNAME, name, surname)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTO.write(this.actorDTO).getJson()));
    }

    @Test
    void testGivenNotExistentActorWhenGetActorByNameAndSurnameThenReturnNotFound() throws Exception {
        String name = "Pol";
        String surname = "Farreny";
        given(this.actorService.getActorByNameAndSurname(name, surname)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(ActorController.ACTORS + ActorController.NAME + ActorController.SURNAME, name, surname)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenCorrectActorDTOWhenCreateActorThenReturnCreated() throws Exception {
        given(this.actorService.createActor(this.actorDTO.toActorEntity())).willReturn(this.actor);

        MockHttpServletResponse response = this.mvc.perform(
                post(ActorController.ACTORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonActorDTO.write(this.actorDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTO.write(this.actorDTO).getJson()));
    }

    @Test
    void testGivenIncorrectActorDTOWhenCreateActorThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = this.mvc.perform(
                post(ActorController.ACTORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NULL")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectActorDTOAndCorrectIdWhenUpdateActorThenReturnOk() throws Exception {
        int id = 1;
        given(this.actorService.updateActor(id, this.actorDTO.toActorEntity())).willReturn(this.actor);

        MockHttpServletResponse response = this.mvc.perform(
                put(ActorController.ACTORS + ActorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonActorDTO.write(this.actorDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonActorDTO.write(this.actorDTO).getJson()));
    }

    @Test
    void testGivenIncorrectActorDTOWhenUpdateActorThenReturnBadRequest() throws Exception {
        int id = 1;

        MockHttpServletResponse response = this.mvc.perform(
                put(ActorController.ACTORS + ActorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NULL")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectActorDTOAndIncorrectIdWhenUpdateActorThenReturnNotFound() throws Exception {
        int id = 1;
        given(this.actorService.updateActor(id, this.actorDTO.toActorEntity())).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                put(ActorController.ACTORS + ActorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonActorDTO.write(this.actorDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenCorrectActorIdWhenDeleteActorThenReturnNoContent() throws Exception {
        int id = 1;

        MockHttpServletResponse response = this.mvc.perform(
                delete(ActorController.ACTORS + ActorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

}