package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.DirectorDTO;
import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.domain.exceptions.GenderException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.domain.services.DirectorService;
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
@WebMvcTest(DirectorController.class)
public class DirectorControllerTest {

    @MockBean
    private DirectorService directorService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<DirectorDTO>> jsonDirectorDTOList;

    @Autowired
    private JacksonTester<DirectorDTO> jsonDirectorDTO;

    private DirectorDTO directorDTO;

    private DirectorEntity director;

    private List<DirectorDTO> directorsDTO;

    private List<DirectorEntity> directors;

    @BeforeEach
    void setUp() {
        this.director = new DirectorEntity(1,"Pol","Farreny","Spain",24, GenderType.MALE,null);
        this.directorDTO = new DirectorDTO(this.director);
        this.directors = new ArrayList<>();
        this.directors.add(this.director);
        this.directorsDTO = new ArrayList<>();
        this.directorsDTO.add(this.directorDTO);
    }

    @Test
    void testGivenSavedDirectorsWhenGetAllDirectorsThenReturnOk() throws Exception {
        int page = 0;
        int size = 1;
        given(this.directorService.getAllDirectors(PageRequest.of(page,size))).willReturn(this.directors);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTOList.write(this.directorsDTO).getJson()));
    }

    @Test
    void testGivenNonSavedDirectorsWhenGetAllDirectorsThenReturnNotFound() throws Exception {
        int page = 0;
        int size = 1;
        given(this.directorService.getAllDirectors(PageRequest.of(page,size))).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedDirectorsWhenGetDirectorsByAgeThenReturnOk() throws Exception {
        int age = 60;
        given(this.directorService.getDirectorsByAge(age)).willReturn(this.directors);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.AGE, age)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTOList.write(this.directorsDTO).getJson()));
    }

    @Test
    void testGivenNonSavedDirectorsWhenGetDirectorsByAgeThenReturnNotFound() throws Exception {
        int age = 60;
        given(this.directorService.getDirectorsByAge(age)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.AGE, age)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedDirectorsWhenGetDirectorsByCountryThenReturnOk() throws Exception {
        String country = "Italy";
        given(this.directorService.getDirectorsByCountry(country)).willReturn(this.directors);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.COUNTRY, country)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTOList.write(this.directorsDTO).getJson()));
    }

    @Test
    void testGivenNonSavedDirectorsWhenGetDirectorsByCountryThenReturnNotFound() throws Exception {
        String country = "Italy";
        given(this.directorService.getDirectorsByCountry(country)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.COUNTRY, country)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedDirectorsWhenGetDirectorsByCorrectGenderThenReturnOk() throws Exception {
        String gender = "FEMALE";
        given(this.directorService.getDirectorsByGender(gender)).willReturn(this.directors);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.GENDER, gender)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTOList.write(this.directorsDTO).getJson()));
    }

    @Test
    void testGivenSavedDirectorsWhenGetDirectorsByIncorrectGenderThenReturnBadRequest() throws Exception {
        String gender = "NULL";
        given(this.directorService.getDirectorsByGender(gender)).willThrow(GenderException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.GENDER, gender)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenNonSavedDirectorsWhenGetDirectorsByCorrectGenderThenReturnNotFound() throws Exception {
        String gender = "MALE";
        given(this.directorService.getDirectorsByGender(gender)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.GENDER, gender)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenExistentDirectorWhenGetDirectorByNameAndSurnameThenReturnOk() throws Exception {
        String name = "Pol";
        String surname = "Farreny";
        given(this.directorService.getDirectorByNameAndSurname(name, surname)).willReturn(this.director);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.NAME + DirectorController.SURNAME, name, surname)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTO.write(this.directorDTO).getJson()));
    }

    @Test
    void testGivenNotExistentDirectorWhenGetDirectorByNameAndSurnameThenReturnNotFound() throws Exception {
        String name = "Pol";
        String surname = "Farreny";
        given(this.directorService.getDirectorByNameAndSurname(name, surname)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(DirectorController.DIRECTORS + DirectorController.NAME + DirectorController.SURNAME, name, surname)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenCorrectDirectorDTOWhenCreateDirectorThenReturnCreated() throws Exception {
        given(this.directorService.createDirector(this.directorDTO.toDirectorEntity())).willReturn(this.director);

        MockHttpServletResponse response = this.mvc.perform(
                post(DirectorController.DIRECTORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonDirectorDTO.write(this.directorDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTO.write(this.directorDTO).getJson()));
    }

    @Test
    void testGivenIncorrectDirectorDTOWhenCreateDirectorThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = this.mvc.perform(
                post(DirectorController.DIRECTORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NULL")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectDirectorDTOAndCorrectIdWhenUpdateDirectorThenReturnOk() throws Exception {
        int id = 1;
        given(this.directorService.updateDirector(id, this.directorDTO.toDirectorEntity())).willReturn(this.director);

        MockHttpServletResponse response = this.mvc.perform(
                put(DirectorController.DIRECTORS + DirectorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonDirectorDTO.write(this.directorDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonDirectorDTO.write(this.directorDTO).getJson()));
    }

    @Test
    void testGivenIncorrectDirectorDTOWhenUpdateDirectorThenReturnBadRequest() throws Exception {
        int id = 1;

        MockHttpServletResponse response = this.mvc.perform(
                put(DirectorController.DIRECTORS + DirectorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NULL")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectDirectorDTOAndIncorrectIdWhenUpdateDirectorThenReturnNotFound() throws Exception {
        int id = 1;
        given(this.directorService.updateDirector(id, this.directorDTO.toDirectorEntity())).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                put(DirectorController.DIRECTORS + DirectorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonDirectorDTO.write(this.directorDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenCorrectDirectorIdWhenDeleteDirectorThenReturnNoContent() throws Exception {
        int id = 1;

        MockHttpServletResponse response = this.mvc.perform(
                delete(DirectorController.DIRECTORS + DirectorController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }
}
