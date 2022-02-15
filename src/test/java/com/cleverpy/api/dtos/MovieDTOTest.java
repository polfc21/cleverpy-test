package com.cleverpy.api.dtos;

import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.MovieEntity;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MovieDTOTest {

    @Test
    void testGivenMovieDTOWhenToMovieEntityThenNewMovieDTOWithMovieEntityDataIsMovieDTO() {
        DirectorDTO directorDTO = new DirectorDTO(null, "Pol", "Farreny",
                "Spain", 24, "MALE");
        MovieDTO movieDTO = new MovieDTO(null, "The Godfather", 1975,
                "Spanish", 177, "CRIME", directorDTO, null);

        MovieEntity movie = movieDTO.toMovieEntity();
        DirectorEntity director = directorDTO.toDirectorEntity();
        movie.setDirector(director);

        assertThat(movieDTO, is(new MovieDTO(movie)));
    }
}
