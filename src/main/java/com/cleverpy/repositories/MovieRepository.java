package com.cleverpy.repositories;

import com.cleverpy.entities.FilmGenreType;
import com.cleverpy.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    List<MovieEntity> getMovieEntitiesByFilmGenreType(FilmGenreType filmGenreType);
    List<MovieEntity> getMovieEntitiesByYear(Integer year);
    List<MovieEntity> getMovieEntitiesByLanguage(String language);
    List<MovieEntity> getMovieEntitiesByDuration(Integer duration);
    MovieEntity getMovieEntityByTitle(String title);

}
