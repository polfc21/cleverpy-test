package com.cleverpy.data.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ActorEntityTest {

    @Test
    void testGivenActorWhenDeleteActorInCastThenSizeZero() {
        ActorEntity actor = new ActorEntity();
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> moviesActed = new ArrayList<>();
        List<ActorEntity> cast = new ArrayList<>();
        moviesActed.add(movie);
        cast.add(actor);
        actor.setMoviesActed(moviesActed);
        movie.setCast(cast);

        actor.deleteActorInMoviesActed();

        assertThat(actor.getMoviesActed().size(), is(1));
        assertThat(movie.getCast().size(), is(0));
    }
}
