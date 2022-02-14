package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.MovieDTO;
import com.cleverpy.domain.services.MovieService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping(MovieController.MOVIES)
@Api(tags = "API Rest. Movie management.")
public class MovieController {

    public static final String MOVIES = "/movies";
    public static final String FILM_GENRE = "/filmGenre/{filmGenre}";
    public static final String YEAR = "/year/{year}";
    public static final String LANGUAGE = "/language/{language}";
    public static final String DURATION = "/duration/{duration}";
    public static final String TITLE = "/title/{title}";
    public static final String ID = "/{id}";
    public static final String DIRECTOR_ID = "/director/{directorId}";

    public static final String OK_MOVIE_MESSAGE = "Response ok if the movie was found";
    public static final String OK_MOVIES_MESSAGE = "Response ok if the movies were found";
    public static final String OK_UPDATED_MESSAGE = "Response ok if the movie was updated";
    public static final String CREATED_MESSAGE = "Response created if the movie was created";
    public static final String NO_CONTENT_DELETED_MESSAGE = "Response no content if the movie was deleted";
    public static final String NOT_FOUND_MOVIE_MESSAGE = "Response not found if the movie doesn't exists";
    public static final String NOT_FOUND_MOVIES_MESSAGE = "Response not found if there aren't movies";
    public static final String BAD_REQUEST_FILM_GENRE_MESSAGE = "Response bad request if the film genre is incorrect";
    public static final String BAD_REQUEST_INCORRECT_MOVIE_MESSAGE = "Response bad request if the movie provided is not correct";
    public static final String UNAUTHORIZED_MESSAGE = "Response access denied if the role is not granted for this method";

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @ApiOperation(notes = "Retrieve all movies that are saved in the database", value = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_MOVIES_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIES_MESSAGE)
    })
    public ResponseEntity<List<MovieDTO>> getAllMovies(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size) {
        List<MovieDTO> moviesDTO = this.movieService.getAllMovies(PageRequest.of(page, size))
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.FILM_GENRE)
    @ApiOperation(notes = "Retrieve all movies that match with film genre passed by path", value = "Get movies by film genre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_MOVIES_MESSAGE),
            @ApiResponse(code = 400, message = MovieController.BAD_REQUEST_FILM_GENRE_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIES_MESSAGE)
    })
    public ResponseEntity<List<MovieDTO>> getMoviesByFilmGenre(
            @ApiParam(example = "Crime", value = "Film Genre Type", allowableValues = "action, comedy, crime, fantasy, horror, thriller", required = true)
            @PathVariable String filmGenre) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByFilmGenre(filmGenre)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.YEAR)
    @ApiOperation(notes = "Retrieve all movies that match with year passed by path", value = "Get movies by year")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_MOVIES_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIES_MESSAGE)
    })
    public ResponseEntity<List<MovieDTO>> getMoviesByYear(
            @ApiParam(example = "1997", value = "Year", allowableValues = "1895, 1946, 1976, 2001, 2016, 2022", required = true)
            @PathVariable Integer year) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByYear(year)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.LANGUAGE)
    @ApiOperation(notes = "Retrieve all movies that match with language passed by path", value = "Get movies by language")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_MOVIES_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIES_MESSAGE)
    })
    public ResponseEntity<List<MovieDTO>> getMoviesByLanguage(
            @ApiParam(example = "Spanish", value = "Language", allowableValues = "Spanish, English, Italian", required = true)
            @PathVariable String language) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByLanguage(language)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.DURATION)
    @ApiOperation(notes = "Retrieve all movies that match with duration (in minutes) passed by path", value = "Get movies by duration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_MOVIES_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIES_MESSAGE)
    })
    public ResponseEntity<List<MovieDTO>> getMoviesByDuration(
            @ApiParam(example = "156", value = "Duration", allowableValues = "123, 97, 187", required = true)
            @PathVariable Integer duration) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByDuration(duration)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.TITLE)
    @ApiOperation(notes = "Retrieve movie that match with title passed by path", value = "Get movie by title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_MOVIE_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIE_MESSAGE)
    })
    public ResponseEntity<MovieDTO> getMoviesByTitle(
            @ApiParam(example = "The Godfather", value = "Title", allowableValues = "The Godfather, The Wolf of Wall Street, Scarface", required = true)
            @PathVariable String title) {
        MovieDTO movieDTO = new MovieDTO(this.movieService.getMovieByTitle(title));
        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(MovieController.DIRECTOR_ID)
    @ApiOperation(notes = "Create movie with data passed by request body", value = "Create movie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = MovieController.CREATED_MESSAGE),
            @ApiResponse(code = 400, message = MovieController.BAD_REQUEST_INCORRECT_MOVIE_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTOR_MESSAGE)
    })
    public ResponseEntity<MovieDTO> createMovie(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer directorId,
            @Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO movieDTOCreated = new MovieDTO(this.movieService.createMovie(directorId, movieDTO.toMovieEntity()));
        return new ResponseEntity<>(movieDTOCreated, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(MovieController.ID)
    @ApiOperation(notes = "Update movie by id passed by path and data passed by request body", value = "Update movie by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = MovieController.OK_UPDATED_MESSAGE),
            @ApiResponse(code = 400, message = MovieController.BAD_REQUEST_INCORRECT_MOVIE_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIE_MESSAGE)
    })
    public ResponseEntity<MovieDTO> updateMovie(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO movieDTOUpdated = new MovieDTO(this.movieService.updateMovie(id, movieDTO.toMovieEntity()));
        return new ResponseEntity<>(movieDTOUpdated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(MovieController.ID)
    @ApiOperation(notes = "Delete movie by id passed by path", value = "Delete movie by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = MovieController.NO_CONTENT_DELETED_MESSAGE),
            @ApiResponse(code = 401, message = MovieController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = MovieController.NOT_FOUND_MOVIE_MESSAGE)
    })
    public ResponseEntity<HttpStatus> deleteMovie(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer id) {
        this.movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
