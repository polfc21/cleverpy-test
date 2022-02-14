package com.cleverpy.domain.services;

import com.cleverpy.data.entities.MovieEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    List<MovieEntity> getAllMovies(Pageable pageable);
    List<MovieEntity> getMoviesByFilmGenre(String filmGenre);
    List<MovieEntity> getMoviesByYear(Integer year);
    List<MovieEntity> getMoviesByLanguage(String language);
    List<MovieEntity> getMoviesByDuration(Integer duration);
    MovieEntity getMovieByTitle(String title);
    MovieEntity getMovieById(Integer id);
    MovieEntity createMovie(Integer directorId, MovieEntity movie);
    MovieEntity updateMovie(Integer id, MovieEntity movie);
    void deleteMovie(Integer id);
    MovieEntity addActorByMovieIdAndActorId(Integer movieId, Integer actorId);

}
