package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.FilmGenreType;
import com.cleverpy.data.entities.MovieEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGivenMovieOfComedyWhenGetMoviesByFilmGenreComedyThenReturnListOfSizeOne() {
        MovieEntity movie = new MovieEntity();
        movie.setFilmGenreType(FilmGenreType.COMEDY);
        this.testEntityManager.persist(movie);

        List<MovieEntity> movies =
                this.movieRepository.getMovieEntitiesByFilmGenreType(FilmGenreType.COMEDY);

        assertThat(movies.size(), is(1));
    }

    @Test
    void testGivenMovieOf1970WhenGetMoviesByYear2000ThenReturnListOfSizeZero() {
        MovieEntity movie = new MovieEntity();
        movie.setYear(1970);
        this.testEntityManager.persist(movie);

        List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByYear(2000);

        assertThat(movies.size(), is(0));
    }

    @Test
    void testGivenMovieSpanishWhenGetMoviesByLanguageItalianThenReturnListOfSizeZero() {
        MovieEntity movie = new MovieEntity();
        movie.setLanguage("Spanish");
        this.testEntityManager.persist(movie);

        List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByLanguage("Italian");

        assertThat(movies.size(), is(0));
    }

    @Test
    void testGivenMovieOf100DurationWhenGetMoviesByDuration100ThenReturnListOfSizeOne() {
        MovieEntity movie = new MovieEntity();
        movie.setDuration(100);
        this.testEntityManager.persist(movie);

        List<MovieEntity> movies = this.movieRepository.getMovieEntitiesByDuration(100);

        assertThat(movies.size(), is(1));
    }

    @Test
    void testGivenMovieWithSawTitleWhenGetMovieByTitleTitanicThenReturnNull() {
        MovieEntity saw = new MovieEntity();
        saw.setTitle("SAW");
        this.testEntityManager.persist(saw);

        MovieEntity titanic = this.movieRepository.getMovieEntityByTitle("Titanic");

        Assertions.assertNull(titanic);
    }
}
