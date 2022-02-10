package com.cleverpy.controllers;

import com.cleverpy.dtos.MovieDTO;
import com.cleverpy.services.MovieService;
import com.cleverpy.validators.FilmGenre;
import com.cleverpy.validators.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(MovieController.MOVIES)
public class MovieController {

    public static final String MOVIES = "/movies";
    public static final String FILM_GENRE = "/filmGenre/{filmGenre}";
    public static final String YEAR = "/year/{year}";
    public static final String LANGUAGE = "/language/{language}";
    public static final String DURATION = "/duration/{duration}";
    public static final String TITLE = "/title/{title}";
    public static final String ID = "/{id}";

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> moviesDTO = this.movieService.getAllMovies();
        if (moviesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.FILM_GENRE)
    public ResponseEntity<List<MovieDTO>> getMoviesByFilmGenre(@PathVariable @FilmGenre String filmGenre) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByFilmGenre(filmGenre);
        if (moviesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.YEAR)
    public ResponseEntity<List<MovieDTO>> getMoviesByYear(@PathVariable @Year Integer year) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByYear(year);
        if (moviesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.LANGUAGE)
    public ResponseEntity<List<MovieDTO>> getMoviesByLanguage(@PathVariable @NotBlank String language) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByLanguage(language);
        if (moviesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.DURATION)
    public ResponseEntity<List<MovieDTO>> getMoviesByDuration(@PathVariable @Positive Integer duration) {
        List<MovieDTO> moviesDTO = this.movieService.getMoviesByDuration(duration);
        if (moviesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(moviesDTO, HttpStatus.OK);
    }

    @GetMapping(MovieController.TITLE)
    public ResponseEntity<MovieDTO> getMoviesByTitle(@PathVariable @NotBlank String title) {
        MovieDTO movieDTO = this.movieService.getMovieByTitle(title);
        if (movieDTO == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO movieDTOCreated = this.movieService.createMovie(movieDTO);
        return new ResponseEntity<>(movieDTOCreated, HttpStatus.OK);
    }

    @PutMapping(MovieController.ID)
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Integer id, @Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO movieDTOUpdated = this.movieService.updateMovie(id, movieDTO);
        return new ResponseEntity<>(movieDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping(MovieController.ID)
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable Integer id) {
        this.movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
