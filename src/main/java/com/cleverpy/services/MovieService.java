package com.cleverpy.services;

import com.cleverpy.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();
    List<MovieDTO> getMoviesByFilmGenre(String filmGenre);
    List<MovieDTO> getMoviesByYear(Integer year);
    List<MovieDTO> getMoviesByLanguage(String language);
    List<MovieDTO> getMoviesByDuration(Integer duration);
    MovieDTO getMovieByTitle(String title);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO updateMovie(Integer id, MovieDTO movieDTO);
    void deleteMovie(Integer id);

}
