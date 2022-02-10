package com.cleverpy.services;

import com.cleverpy.dtos.MovieDTO;
import com.cleverpy.entities.FilmGenreType;
import com.cleverpy.entities.MovieEntity;
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

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return this.movieRepository.findAll()
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByFilmGenre(String filmGenre) {
        FilmGenreType filmGenreType = FilmGenreType.valueOf(filmGenre);
        return this.movieRepository.getMovieEntitiesByFilmGenreType(filmGenreType)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByYear(Integer year) {
        return this.movieRepository.getMovieEntitiesByYear(year)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByLanguage(String language) {
        return this.movieRepository.getMovieEntitiesByLanguage(language)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByDuration(Integer duration) {
        return this.movieRepository.getMovieEntitiesByDuration(duration)
                .stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovieByTitle(String title) {
        MovieEntity movieEntity = this.movieRepository.getMovieEntityByTitle(title);
        return new MovieDTO(movieEntity);
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        MovieEntity movieEntity = this.movieRepository.save(movieDTO.toMovieEntity());
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
            FilmGenreType filmGenreType = FilmGenreType.valueOf(movieDTO.getFilmGenre());
            movieEntity.setFilmGenreType(filmGenreType);
            this.movieRepository.save(movieEntity);
            return new MovieDTO(movieEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteMovie(Integer id) {
        if (this.movieRepository.existsById(id)) {
            this.movieRepository.deleteById(id);
        }
    }

}
