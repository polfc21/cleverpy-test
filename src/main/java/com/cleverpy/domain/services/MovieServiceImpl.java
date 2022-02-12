package com.cleverpy.domain.services;

import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.FilmGenreType;
import com.cleverpy.data.entities.MovieEntity;
import com.cleverpy.domain.exceptions.FilmGenreException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.data.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final DirectorService directorService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, DirectorService directorService) {
        this.movieRepository = movieRepository;
        this.directorService = directorService;
    }

    @Override
    public List<MovieEntity> getAllMovies() {
        List<MovieEntity> movies = this.movieRepository.findAll();
        if (movies.isEmpty()) {
            throw new NotFoundException("No movies found in the database.");
        }
        return movies;
    }

    @Override
    public List<MovieEntity> getMoviesByFilmGenre(String filmGenre) {
        if (this.existsFilmGenre(filmGenre)) {
            List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByFilmGenreType(this.getFilmGenreType(filmGenre));
            if (movies.isEmpty()) {
                throw new NotFoundException("No movies found with " + filmGenre + " genre.");
            }
            return movies;
        }
        throw new FilmGenreException("Film genre " + filmGenre + " no registered in the database.");
    }

    private boolean existsFilmGenre(String filmGenre) {
        return FilmGenreType.existsFilmGenre(filmGenre.toUpperCase());
    }

    private FilmGenreType getFilmGenreType(String filmGenre) {
        return FilmGenreType.valueOf(filmGenre.toUpperCase());
    }

    @Override
    public List<MovieEntity> getMoviesByYear(Integer year) {
        List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByYear(year);
        if (movies.isEmpty()) {
            throw new NotFoundException("No movies found with " + year + " year.");
        }
        return movies;
    }

    @Override
    public List<MovieEntity> getMoviesByLanguage(String language) {
        List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByLanguage(language);
        if (movies.isEmpty()) {
            throw new NotFoundException("No movies found with " + language + " language.");
        }
        return movies;
    }

    @Override
    public List<MovieEntity> getMoviesByDuration(Integer duration) {
        List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByDuration(duration);
        if (movies.isEmpty()) {
            throw new NotFoundException("No movies found with " + duration + " duration.");
        }
        return movies;
    }

    @Override
    public MovieEntity getMovieByTitle(String title) {
        MovieEntity movie = this.movieRepository.getMovieEntityByTitle(title);
        if (movie == null) {
            throw new NotFoundException("No movie found with " + title + " title.");
        }
        return movie;
    }

    @Override
    public MovieEntity createMovie(Integer directorId, MovieEntity movie) {
        DirectorEntity director = this.directorService.getDirectorById(directorId);
        movie.setDirector(director);
        return this.movieRepository.save(movie);
    }

    @Override
    public MovieEntity updateMovie(Integer id, MovieEntity movie) {
        Optional<MovieEntity> optionalMovie = this.movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            MovieEntity updatedMovie = this.getUpdatedMovie(id, movie);
            return this.movieRepository.save(updatedMovie);
        }
        throw new NotFoundException("No movie found with " + id + " id");
    }

    private MovieEntity getUpdatedMovie(Integer id, MovieEntity movie) {
        MovieEntity updatedMovie = this.movieRepository.getById(id);
        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setYear(movie.getYear());
        updatedMovie.setLanguage(movie.getLanguage());
        updatedMovie.setDuration(movie.getDuration());
        updatedMovie.setFilmGenreType(movie.getFilmGenreType());
        return updatedMovie;
    }

    @Override
    @Transactional
    public void deleteMovie(Integer id) {
        if (!this.movieRepository.existsById(id)) {
            throw new NotFoundException("No movie found with " + id + " id");
        }
        this.movieRepository.deleteById(id);
    }

}
