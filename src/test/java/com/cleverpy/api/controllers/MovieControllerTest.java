package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.MovieDTO;
import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.FilmGenreType;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.data.entities.MovieEntity;
import com.cleverpy.domain.exceptions.ActorInMovieException;
import com.cleverpy.domain.exceptions.FilmGenreException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.domain.services.MovieService;
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
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<MovieDTO>> jsonMovieDTOList;

    @Autowired
    private JacksonTester<MovieDTO> jsonMovieDTO;

    private MovieDTO movieDTO;

    private MovieEntity movie;

    private List<MovieDTO> moviesDTO;

    private List<MovieEntity> movies;

    private DirectorEntity director;

    @BeforeEach
    void setUp() {
        this.director = new DirectorEntity(1,"Pol","Farreny","Spain",24, GenderType.MALE,null);
        this.movie = new MovieEntity(1,"Title",1997,"Spanish",240, FilmGenreType.COMEDY, director, null);
        this.movieDTO = new MovieDTO(this.movie);
        this.movies = new ArrayList<>();
        this.movies.add(this.movie);
        this.moviesDTO = new ArrayList<>();
        this.moviesDTO.add(this.movieDTO);
    }

    @Test
    void testGivenSavedMoviesWhenGetAllMoviesThenReturnOk() throws Exception {
        int page = 0;
        int size = 1;
        given(this.movieService.getAllMovies(PageRequest.of(page,size))).willReturn(this.movies);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTOList.write(this.moviesDTO).getJson()));
    }

    @Test
    void testGivenNonSavedMoviesWhenGetAllMoviesThenReturnNotFound() throws Exception {
        int page = 0;
        int size = 1;
        given(this.movieService.getAllMovies(PageRequest.of(page,size))).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page",String.valueOf(page))
                        .param("size",String.valueOf(size))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedMoviesWhenGetMoviesByCorrectFilmGenreThenReturnOk() throws Exception {
        String filmGenre = "COMEDY";
        given(this.movieService.getMoviesByFilmGenre(filmGenre)).willReturn(this.movies);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.FILM_GENRE, filmGenre)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTOList.write(this.moviesDTO).getJson()));
    }

    @Test
    void testGivenSavedDirectorsWhenGetDirectorsByIncorrectGenderThenReturnBadRequest() throws Exception {
        String filmGenre = "NULL";
        given(this.movieService.getMoviesByFilmGenre(filmGenre)).willThrow(FilmGenreException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.FILM_GENRE, filmGenre)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenNonSavedDirectorsWhenGetDirectorsByCorrectGenderThenReturnNotFound() throws Exception {
        String filmGenre = "ACTION";
        given(this.movieService.getMoviesByFilmGenre(filmGenre)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.FILM_GENRE, filmGenre)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedMoviesWhenGetMoviesByYearThenReturnOk() throws Exception {
        int year = 1997;
        given(this.movieService.getMoviesByYear(year)).willReturn(this.movies);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.YEAR, year)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTOList.write(this.moviesDTO).getJson()));
    }

    @Test
    void testGivenNonSavedMoviesWhenGetMoviesByYearThenReturnNotFound() throws Exception {
        int year = 1997;
        given(this.movieService.getMoviesByYear(year)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.YEAR, year)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedDirectorsWhenGetDirectorsByLanguageThenReturnOk() throws Exception {
        String language = "English";
        given(this.movieService.getMoviesByLanguage(language)).willReturn(this.movies);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.LANGUAGE, language)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTOList.write(this.moviesDTO).getJson()));
    }

    @Test
    void testGivenNonSavedMoviesWhenGetMoviesByLanguageThenReturnNotFound() throws Exception {
        String language = "English";
        given(this.movieService.getMoviesByLanguage(language)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.LANGUAGE, language)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenSavedMoviesWhenGetMoviesByDurationThenReturnOk() throws Exception {
        int duration = 177;
        given(this.movieService.getMoviesByDuration(duration)).willReturn(this.movies);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.DURATION, duration)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTOList.write(this.moviesDTO).getJson()));
    }

    @Test
    void testGivenNonSavedMoviesWhenGetMoviesByDurationThenReturnNotFound() throws Exception {
        int duration = 97;
        given(this.movieService.getMoviesByDuration(duration)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.DURATION, duration)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenExistentMovieWhenGetMovieByTitleThenReturnOk() throws Exception {
        String title = "Gladiator";
        given(this.movieService.getMovieByTitle(title)).willReturn(this.movie);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.TITLE, title)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTO.write(this.movieDTO).getJson()));
    }

    @Test
    void testGivenNonExistentMovieWhenGetMovieByTitleThenReturnNotFound() throws Exception {
        String title = "Gladiator";
        given(this.movieService.getMovieByTitle(title)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                get(MovieController.MOVIES + MovieController.TITLE, title)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenCorrectMovieDTOAndExistentDirectorWhenCreateMovieThenReturnCreated() throws Exception {
        int directorId = 1;
        given(this.movieService.createMovie(directorId,this.movieDTO.toMovieEntity())).willReturn(this.movie);

        MockHttpServletResponse response = this.mvc.perform(
                post(MovieController.MOVIES + MovieController.DIRECTOR_ID, directorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonMovieDTO.write(this.movieDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTO.write(this.movieDTO).getJson()));
    }

    @Test
    void testGivenCorrectMovieDTOAndNonExistentDirectorWhenCreateMovieThenReturnNotFound() throws Exception {
        int directorId = 10;
        given(this.movieService.createMovie(directorId,this.movieDTO.toMovieEntity())).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                post(MovieController.MOVIES + MovieController.DIRECTOR_ID, directorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonMovieDTO.write(this.movieDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenIncorrectMovieDTOWhenCreateMovieThenReturnBadRequest() throws Exception {
        MockHttpServletResponse response = this.mvc.perform(
                post(MovieController.MOVIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NULL")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectMovieDTOAndCorrectIdWhenUpdateMovieThenReturnOk() throws Exception {
        int id = 1;
        given(this.movieService.updateMovie(id, this.movieDTO.toMovieEntity())).willReturn(this.movie);

        MockHttpServletResponse response = this.mvc.perform(
                put(MovieController.MOVIES + MovieController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonMovieDTO.write(this.movieDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTO.write(this.movieDTO).getJson()));
    }

    @Test
    void testGivenIncorrectMovieDTOWhenUpdateMovieThenReturnBadRequest() throws Exception {
        int id = 1;

        MockHttpServletResponse response = this.mvc.perform(
                put(MovieController.MOVIES + MovieController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("NULL")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenCorrectMovieDTOAndIncorrectIdWhenUpdateMovieThenReturnNotFound() throws Exception {
        int id = 1;
        given(this.movieService.updateMovie(id, this.movieDTO.toMovieEntity())).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mvc.perform(
                put(MovieController.MOVIES + MovieController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonMovieDTO.write(this.movieDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenIncorrectMovieIdWhenDeleteMovieThenReturnNotFound() throws Exception {
        int id = 1;

        MockHttpServletResponse response = this.mvc.perform(
                delete(MovieController.MOVIES + MovieController.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void testGivenActorAlreadyInMovieWhenAddActorToMovieThenReturnBadRequest() throws Exception {
        int id = 1;
        int actorId = 1;
        given(this.movieService.addActorByMovieIdAndActorId(id, actorId)).willThrow(ActorInMovieException.class);
        MockHttpServletResponse response = this.mvc.perform(
                put(MovieController.MOVIES + MovieController.ID + MovieController.ACTOR_ID, id, actorId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void testGivenActorIdNotExistentWhenAddActorToMovieThenReturnNotFound() throws Exception {
        int id = 1;
        int actorId = 1;
        given(this.movieService.addActorByMovieIdAndActorId(id, actorId)).willThrow(NotFoundException.class);
        MockHttpServletResponse response = this.mvc.perform(
                put(MovieController.MOVIES + MovieController.ID + MovieController.ACTOR_ID, id, actorId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGivenActorIdExistentWhenAddActorToMovieThenReturnOk() throws Exception {
        int id = 1;
        int actorId = 1;
        given(this.movieService.addActorByMovieIdAndActorId(id, actorId)).willReturn(this.movie);
        MockHttpServletResponse response = this.mvc.perform(
                put(MovieController.MOVIES + MovieController.ID + MovieController.ACTOR_ID, id, actorId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonMovieDTO.write(this.movieDTO).getJson()));
    }
}
