package com.cleverpy.data.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MovieEntityTest {

    @Test
    void testGivenActorWhenAddActorInEmptyCastThenSizeOne() {
        MovieEntity movie = new MovieEntity();
        List<ActorEntity> cast = new ArrayList<>();
        movie.setCast(cast);

        movie.addActor(new ActorEntity());

        assertThat(movie.getCast().size(), is(1));
    }

    @Test
    void testGivenActorWhenDeleteActorInCastThenSizeZero() {
        MovieEntity movie = new MovieEntity();
        List<ActorEntity> cast = new ArrayList<>();
        ActorEntity actor = new ActorEntity();
        cast.add(actor);
        movie.setCast(cast);

        movie.deleteActor(actor);

        assertThat(movie.getCast().size(), is(0));
    }

    @Test
    void testGivenActorOutOfCastWhenContainsActorThenReturnFalse() {
        MovieEntity movie = new MovieEntity();
        List<ActorEntity> cast = new ArrayList<>();
        movie.setCast(cast);
        ActorEntity actor = new ActorEntity();

        boolean isContained = movie.containsActor(actor);

        assertThat(isContained, is(false));
    }

    @Test
    void testGivenMovieWhenDeleteMovieInActorOfCastThenSizeZero() {
        ActorEntity actor = new ActorEntity();
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> moviesActed = new ArrayList<>();
        List<ActorEntity> cast = new ArrayList<>();
        moviesActed.add(movie);
        cast.add(actor);
        actor.setMoviesActed(moviesActed);
        movie.setCast(cast);

        movie.deleteMovieInActorsOfCast();

        assertThat(actor.getMoviesActed().size(), is(0));
        assertThat(movie.getCast().size(), is(1));
    }
}
