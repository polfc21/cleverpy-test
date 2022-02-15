package com.cleverpy.data.entities;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FilmGenreTypeTest {

    @Test
    void testGivenExistentFilmGenreWhenExistsFilmGenreThenReturnTrue() {
        assertThat(FilmGenreType.existsFilmGenre("action"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("comedy"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("crime"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("drama"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("fantasy"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("horror"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("suspense"), is(true));
        assertThat(FilmGenreType.existsFilmGenre("thriller"), is(true));
    }

    @Test
    void testGivenNotExistentFilmGenreWhenExistsFilmGenreThenReturnFalse() {
        assertThat(FilmGenreType.existsFilmGenre("null"), is(false));
    }
}
