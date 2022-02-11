package com.cleverpy.services;

import com.cleverpy.dtos.MovieDTO;
import com.cleverpy.entities.DirectorEntity;
import com.cleverpy.entities.FilmGenreType;
import com.cleverpy.entities.MovieEntity;
import com.cleverpy.exceptions.FilmGenreException;
import com.cleverpy.exceptions.NotFoundException;
import com.cleverpy.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final DirectorService directorService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, DirectorService directorService) {
        this.movieRepository = movieRepository;
        this.directorService = directorService;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<MovieDTO> moviesDTO = this.movieRepository.findAll()
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        if (moviesDTO.isEmpty()) {
            throw new NotFoundException("No movies found in the database.");
        }
        return moviesDTO;
    }

    @Override
    public List<MovieDTO> getMoviesByFilmGenre(String filmGenre) {
        if (this.existsFilmGenre(filmGenre)) {
            List<MovieDTO> moviesDTO =
                    this.movieRepository.getMovieEntitiesByFilmGenreType(this.getFilmGenreType(filmGenre))
                    .stream()
                    .map(MovieDTO::new)
                    .collect(Collectors.toList());
            if (moviesDTO.isEmpty()) {
                throw new NotFoundException("No movies found with " + filmGenre + " genre.");
            }
            return moviesDTO;
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
    public List<MovieDTO> getMoviesByYear(Integer year) {
        List<MovieDTO> moviesDTO = this.movieRepository.getMovieEntitiesByYear(year)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        if (moviesDTO.isEmpty()) {
            throw new NotFoundException("No movies found with " + year + " year.");
        }
        return moviesDTO;
    }

    @Override
    public List<MovieDTO> getMoviesByLanguage(String language) {
        List<MovieDTO> moviesDTO = this.movieRepository.getMovieEntitiesByLanguage(language)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        if (moviesDTO.isEmpty()) {
            throw new NotFoundException("No movies found with " + language + " language.");
        }
        return moviesDTO;
    }

    @Override
    public List<MovieDTO> getMoviesByDuration(Integer duration) {
        List<MovieDTO> moviesDTO = this.movieRepository.getMovieEntitiesByDuration(duration)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
        if (moviesDTO.isEmpty()) {
            throw new NotFoundException("No movies found with " + duration + " duration.");
        }
        return moviesDTO;
    }

    @Override
    public MovieDTO getMovieByTitle(String title) {
        MovieEntity movieEntity = this.movieRepository.getMovieEntityByTitle(title);
        if (movieEntity == null) {
            throw new NotFoundException("No movie found with " + title + " title.");
        }
        return new MovieDTO(movieEntity);
    }

    @Override
    public MovieDTO createMovie(Integer directorId, MovieDTO movieDTO) {
        DirectorEntity directorEntity = this.directorService.getDirectorById(directorId);
        MovieEntity movieEntity = this.movieRepository.save(movieDTO.toMovieEntity(directorEntity));
        return new MovieDTO(movieEntity);
    }

    @Override
    public MovieDTO updateMovie(Integer id, MovieDTO movieDTO) {
        Optional<MovieEntity> optionalMovie = this.movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            MovieEntity movieEntity = this.movieRepository.getById(id);
            movieEntity.setTitle(movieDTO.getTitle());
            movieEntity.setYear(movieDTO.getYear());
            movieEntity.setLanguage(movieDTO.getLanguage());
            movieEntity.setDuration(movieDTO.getDuration());
            FilmGenreType filmGenreType = FilmGenreType.valueOf(movieDTO.getFilmGenre().toUpperCase());
            movieEntity.setFilmGenreType(filmGenreType);
            this.movieRepository.save(movieEntity);
            return new MovieDTO(movieEntity);
        }
        throw new NotFoundException("No movie found with " + id + " id");
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
