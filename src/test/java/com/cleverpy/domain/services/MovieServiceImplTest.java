package com.cleverpy.domain.services;

import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.FilmGenreType;
import com.cleverpy.data.entities.MovieEntity;
import com.cleverpy.data.repositories.MovieRepository;
import com.cleverpy.domain.exceptions.ActorInMovieException;
import com.cleverpy.domain.exceptions.FilmGenreException;
import com.cleverpy.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private DirectorService directorService;

    @Mock
    private ActorService actorService;

    @Test
    void testGivenNonSavedMoviesWhenGetAllMoviesThenThrowNotFoundException() {
        Pageable pageable = PageRequest.of(0,2);

        when(this.movieRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(NotFoundException.class, () -> this.movieService.getAllMovies(pageable));
    }

    @Test
    void testGivenSavedMoviesWhenGetAllMoviesThenReturnMovies() {
        Pageable pageable = PageRequest.of(0,1);
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> movies = new ArrayList<>();
        movies.add(movie);
        Page<MovieEntity> pageMovies = new PageImpl<>(movies);

        when(this.movieRepository.findAll(pageable)).thenReturn(pageMovies);

        assertThat(this.movieService.getAllMovies(pageable), is(movies));
    }

    @Test
    void testGivenIncorrectFilmGenreWhenGetMoviesByFilmGenreThenThrowFilmGenreException() {
        String incorrectFilmGenre = "null";

        assertThrows(FilmGenreException.class, () -> this.movieService.getMoviesByFilmGenre(incorrectFilmGenre));
    }

    @Test
    void testGivenNonSavedComedyMoviesWhenGetMoviesByComedyFilmGenreThenThrowNotFoundException() {
        List<MovieEntity> emptyList = new ArrayList<>();

        when(this.movieRepository.getMovieEntitiesByFilmGenreType(FilmGenreType.COMEDY)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.movieService.getMoviesByFilmGenre("comedy"));
    }

    @Test
    void testGivenSavedHorrorMoviesWhenGetMoviesByHorrorFilmGenreThenReturnMovies() {
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> horrorMovies = new ArrayList<>();
        horrorMovies.add(movie);

        when(this.movieRepository.getMovieEntitiesByFilmGenreType(FilmGenreType.HORROR)).thenReturn(horrorMovies);

        assertThat(this.movieService.getMoviesByFilmGenre("horror"), is(horrorMovies));
    }

    @Test
    void testGivenNonSavedMoviesOf1970YearWhenGetMoviesBy1970YearThenThrowNotFoundException() {
        List<MovieEntity> emptyList = new ArrayList<>();

        when(this.movieRepository.getMovieEntitiesByYear(1970)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.movieService.getMoviesByYear(1970));
    }

    @Test
    void testGivenSavedMoviesOf2000YearWhenGetMoviesBy2000YearThenReturnMovies() {
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> twoThousandMovies = new ArrayList<>();
        twoThousandMovies.add(movie);

        when(this.movieRepository.getMovieEntitiesByYear(2000)).thenReturn(twoThousandMovies);

        assertThat(this.movieService.getMoviesByYear(2000), is(twoThousandMovies));
    }

    @Test
    void testGivenNonSavedItalianMoviesWhenGetMoviesByItalianLanguageThenThrowNotFoundException() {
        List<MovieEntity> emptyList = new ArrayList<>();

        when(this.movieRepository.getMovieEntitiesByLanguage("Italian")).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.movieService.getMoviesByLanguage("Italian"));
    }

    @Test
    void testGivenSavedSpanishMoviesWhenGetMoviesBySpanishLanguageThenReturnMovies() {
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> spanishMovies = new ArrayList<>();
        spanishMovies.add(movie);

        when(this.movieRepository.getMovieEntitiesByLanguage("Spanish")).thenReturn(spanishMovies);

        assertThat(this.movieService.getMoviesByLanguage("Spanish"), is(spanishMovies));
    }

    @Test
    void testGivenNonSavedMoviesOf100DurationWhenGetMoviesBy100DurationThenThrowNotFoundException() {
        List<MovieEntity> emptyList = new ArrayList<>();

        when(this.movieRepository.getMovieEntitiesByDuration(100)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.movieService.getMoviesByDuration(100));
    }

    @Test
    void testGivenSavedMoviesOf213DurationWhenGetMoviesBy213DurationThenReturnMovies() {
        MovieEntity movie = new MovieEntity();
        List<MovieEntity> twoHundredThirteenMovies = new ArrayList<>();
        twoHundredThirteenMovies.add(movie);

        when(this.movieRepository.getMovieEntitiesByDuration(213)).thenReturn(twoHundredThirteenMovies);

        assertThat(this.movieService.getMoviesByDuration(213), is(twoHundredThirteenMovies));
    }

    @Test
    void testGivenNonSavedMovieWithSawTitleWhenGetMovieBySawTitleThenThrowNotFoundException() {
        when(this.movieRepository.getMovieEntityByTitle("SAW")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.movieService.getMovieByTitle("SAW"));
    }

    @Test
    void testGivenSavedMovieWithTitanicTitleWhenGetMovieByTitanicTitleThenReturnTitanic() {
        MovieEntity titanic = new MovieEntity();

        when(this.movieRepository.getMovieEntityByTitle("Titanic")).thenReturn(titanic);

        assertThat(this.movieService.getMovieByTitle("Titanic"), is(titanic));
    }

    @Test
    void testGivenNonSavedMovieWith1IdWhenGetMovieById1ThenThrowNotFoundException() {
        when(this.movieRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.movieService.getMovieById(1));
    }

    @Test
    void testGivenSavedMovieWith1IdWhenGetMovieById1ThenReturnMovieIdOne() {
        MovieEntity movieIdOne = new MovieEntity();

        when(this.movieRepository.existsById(1)).thenReturn(true);
        when(this.movieRepository.getById(1)).thenReturn(movieIdOne);

        assertThat(this.movieService.getMovieById(1), is(movieIdOne));
    }

    @Test
    void testGivenMovieAndNotPresentDirectorIdWhenCreateMovieThenThrowNotFoundException() {
        when(this.directorService.getDirectorById(1)).thenThrow(NotFoundException.class);

        verify(this.movieRepository, never()).save(null);
        assertThrows(NotFoundException.class, () -> this.movieService.createMovie(1, null));
    }

    @Test
    void testGivenMovieAndPresentDirectorIdWhenCreateMovieThenReturnMovie() {
        DirectorEntity director = new DirectorEntity();
        MovieEntity movie = new MovieEntity();

        when(this.directorService.getDirectorById(1)).thenReturn(director);
        when(this.movieRepository.save(movie)).thenReturn(movie);

        assertThat(this.movieService.createMovie(1, movie), is(movie));
    }

    @Test
    void testGivenNotPresentMovieWith1IdWhenUpdateMovieThenThrowNotFoundException() {
        when(this.movieRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.movieService.updateMovie(1, null));
    }

    @Test
    void testGivenPresentMovieWith1IdWhenUpdateMovieThenReturnUpdatedMovie() {
        MovieEntity movie = new MovieEntity("title",2000,"Spanish",70,"COMEDY");
        MovieEntity updatedMovie = new MovieEntity("title",2000,"English",120,"COMEDY");

        when(this.movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(this.movieRepository.getById(1)).thenReturn(updatedMovie);
        when(this.movieRepository.save(updatedMovie)).thenReturn(updatedMovie);

        assertThat(this.movieService.updateMovie(1, movie), is(updatedMovie));
    }

    @Test
    void testGivenNotPresentMovieWith1IdWhenDeleteMovieThenThrowNotFoundException() {
        when(this.movieRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.movieService.deleteMovie(1));
    }

    @Test
    void testGivenPresentMovieWith1IdWhenDeleteMovieThenMovieDeleted() {
        MovieEntity movie = new MovieEntity();
        ActorEntity actor = new ActorEntity();
        List<MovieEntity> movies = new ArrayList<>();
        List<ActorEntity> cast = new ArrayList<>();
        movies.add(movie);
        cast.add(actor);
        movie.setCast(cast);
        actor.setMoviesActed(movies);

        when(this.movieRepository.existsById(1)).thenReturn(true);
        when(this.movieRepository.getById(1)).thenReturn(movie);
        this.movieService.deleteMovie(1);

        verify(this.movieRepository, times(1)).getById(1);
        verify(this.movieRepository, times(1)).deleteById(1);
    }

    @Test
    void testGivenCorrectActorIdNotContainedInCastOfMovieWithCorrectIdWhenAddActorByMovieIdAndActorIdThenReturnMovie() {
        int presentMovieId = 1;
        int presentActorId = 1;
        MovieEntity movie = new MovieEntity();
        ActorEntity actor = new ActorEntity();
        List<ActorEntity> cast = new ArrayList<>();
        movie.setCast(cast);

        when(this.movieRepository.existsById(presentMovieId)).thenReturn(true);
        when(this.movieRepository.getById(presentMovieId)).thenReturn(movie);
        when(this.actorService.getActorById(presentActorId)).thenReturn(actor);
        when(this.movieRepository.save(movie)).thenReturn(movie);

        assertThat(this.movieService.addActorByMovieIdAndActorId(presentMovieId, presentActorId), is(movie));
    }

    @Test
    void testGivenCorrectActorIdContainedInCastOfMovieWithCorrectIdWhenAddActorByMovieIdAndActorIdThenThrowActionInMovieException() {
        int presentMovieId = 1;
        int presentActorId = 1;
        MovieEntity movie = new MovieEntity();
        ActorEntity actor = new ActorEntity();
        List<ActorEntity> cast = new ArrayList<>();
        cast.add(actor);
        movie.setCast(cast);

        when(this.movieRepository.existsById(presentMovieId)).thenReturn(true);
        when(this.movieRepository.getById(presentMovieId)).thenReturn(movie);
        when(this.actorService.getActorById(presentActorId)).thenReturn(actor);

        assertThrows(ActorInMovieException.class, () -> this.movieService.addActorByMovieIdAndActorId(presentMovieId, presentActorId));
    }

    @Test
    void testGivenNotExistentActorIdAndMovieWithCorrectIdWhenAddActorByMovieIdAndActorIdThenThrowNotFoundException() {
        int presentMovieId = 1;
        int notPresentActorId = 1;
        MovieEntity movie = new MovieEntity();

        when(this.movieRepository.existsById(presentMovieId)).thenReturn(true);
        when(this.movieRepository.getById(presentMovieId)).thenReturn(movie);
        when(this.actorService.getActorById(notPresentActorId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.movieService.addActorByMovieIdAndActorId(presentMovieId, notPresentActorId));
    }

    @Test
    void testGivenNotExistentMovieIdWhenAddActorByMovieIdAndActorIdThenThrowNotFoundException() {
        int notPresentMovieId = 1;
        int presentActorId = 1;

        when(this.movieRepository.existsById(notPresentMovieId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.movieService.addActorByMovieIdAndActorId(notPresentMovieId, presentActorId));
    }
}
