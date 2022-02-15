package com.cleverpy.domain.services;

import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.data.entities.MovieEntity;
import com.cleverpy.data.repositories.ActorRepository;
import com.cleverpy.domain.exceptions.GenderException;
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
public class ActorServiceImplTest {

    @InjectMocks
    private ActorServiceImpl actorService;

    @Mock
    private ActorRepository actorRepository;

    @Test
    void testGivenNonSavedActorsWhenGetAllActorsThenThrowNotFoundException() {
        Pageable pageable = PageRequest.of(0,2);

        when(this.actorRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(NotFoundException.class, () -> this.actorService.getAllActors(pageable));
    }

    @Test
    void testGivenSavedActorsWhenGetAllActorsThenReturnActors() {
        Pageable pageable = PageRequest.of(0,1);
        ActorEntity actor = new ActorEntity();
        List<ActorEntity> actors = new ArrayList<>();
        actors.add(actor);
        Page<ActorEntity> pageActors = new PageImpl<>(actors);

        when(this.actorRepository.findAll(pageable)).thenReturn(pageActors);

        assertThat(this.actorService.getAllActors(pageable), is(actors));
    }

    @Test
    void testGivenNonSavedActorsOf30AgeWhenGetActorsByAge30ThenThrowNotFoundException() {
        List<ActorEntity> emptyList = new ArrayList<>();

        when(this.actorRepository.getActorEntitiesByAge(30)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.actorService.getActorsByAge(30));
    }

    @Test
    void testGivenSavedActorsOf30WhenGetActorsByAge30ThenReturnActors() {
        ActorEntity actor = new ActorEntity();
        List<ActorEntity> thirtyActors = new ArrayList<>();
        thirtyActors.add(actor);

        when(this.actorRepository.getActorEntitiesByAge(30)).thenReturn(thirtyActors);

        assertThat(this.actorService.getActorsByAge(30), is(thirtyActors));
    }

    @Test
    void testGivenNonSavedActorsOfItalyWhenGetActorsByItalyCountryThenThrowNotFoundException() {
        List<ActorEntity> emptyList = new ArrayList<>();

        when(this.actorRepository.getActorEntitiesByCountry("Italy")).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.actorService.getActorsByCountry("Italy"));
    }

    @Test
    void testGivenSavedActorsOfSpainWhenGetActorsBySpainCountryThenReturnActors() {
        ActorEntity actor = new ActorEntity();
        List<ActorEntity> spanishActors = new ArrayList<>();
        spanishActors.add(actor);

        when(this.actorRepository.getActorEntitiesByCountry("Spain")).thenReturn(spanishActors);

        assertThat(this.actorService.getActorsByCountry("Spain"), is(spanishActors));
    }

    @Test
    void testGivenIncorrectGenderWhenGetActorsByGenderThenThrowGenderException() {
        String incorrectGender = "null";

        assertThrows(GenderException.class, () -> this.actorService.getActorsByGender(incorrectGender));
    }

    @Test
    void testGivenNonSavedActorsMaleWhenGetActorsByMaleGenderThenThrowNotFoundException() {
        List<ActorEntity> emptyList = new ArrayList<>();

        when(this.actorRepository.getActorEntitiesByGenderType(GenderType.MALE)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.actorService.getActorsByGender("male"));
    }

    @Test
    void testGivenSavedActorsFemaleWhenGetActorsByFemaleGenderThenReturnActors() {
        ActorEntity actor = new ActorEntity();
        List<ActorEntity> femaleActors = new ArrayList<>();
        femaleActors.add(actor);

        when(this.actorRepository.getActorEntitiesByGenderType(GenderType.FEMALE)).thenReturn(femaleActors);

        assertThat(this.actorService.getActorsByGender("female"), is(femaleActors));
    }

    @Test
    void testGivenNonSavedActorAlNamePacinoSurnameWhenGetActorByNameAndSurnameAlPacinoThenThrowNotFoundException() {
        when(this.actorRepository.getActorEntityByNameAndSurname("Al", "Pacino")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.actorService.getActorByNameAndSurname("Al", "Pacino"));
    }

    @Test
    void testGivenSavedActorAlNamePacinoSurnameWhenGetActorByNameAndSurnameAlPacinoThenReturnAlPacino() {
        ActorEntity alPacino = new ActorEntity();

        when(this.actorRepository.getActorEntityByNameAndSurname("Al","Pacino")).thenReturn(alPacino);

        assertThat(this.actorService.getActorByNameAndSurname("Al", "Pacino"), is(alPacino));
    }

    @Test
    void testGivenNonSavedActorWith1IdWhenGetActorById1ThenThrowNotFoundException() {
        when(this.actorRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.actorService.getActorById(1));
    }

    @Test
    void testGivenSavedActorWith1IdWhenGetActorByIdThenReturnActorId1() {
        ActorEntity actorIdOne = new ActorEntity();

        when(this.actorRepository.existsById(1)).thenReturn(true);
        when(this.actorRepository.getById(1)).thenReturn(actorIdOne);

        assertThat(this.actorService.getActorById(1), is(actorIdOne));
    }

    @Test
    void testGivenActorWhenCreateActorThenReturnActor() {
        ActorEntity actor = new ActorEntity();

        when(this.actorRepository.save(actor)).thenReturn(actor);

        assertThat(this.actorService.createActor(actor), is(actor));
    }

    @Test
    void testGivenNotPresentActorWith1IdWhenUpdateActorThenThrowNotFoundException() {
        when(this.actorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.actorService.updateActor(1, null));
    }

    @Test
    void testGivenPresentActorWith1IdWhenUpdateActorThenReturnActorUpdated() {
        ActorEntity actor = new ActorEntity("name","surname","country",59,"FEMALE");
        ActorEntity updatedActor = new ActorEntity("name","surname","country",30,"FEMALE");

        when(this.actorRepository.findById(1)).thenReturn(Optional.of(actor));
        when(this.actorRepository.getById(1)).thenReturn(updatedActor);
        when(this.actorRepository.save(updatedActor)).thenReturn(updatedActor);

        assertThat(this.actorService.updateActor(1, actor), is(updatedActor));
    }

    @Test
    void testGivenNotPresentActorWith1IdWhenDeleteActorThenThrowNotFoundException() {
        when(this.actorRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.actorService.deleteActor(1));
    }

    @Test
    void testGivenPresentActorWith1IdWhenDeleteActorThenActorDeleted() {
        ActorEntity actor = new ActorEntity();
        MovieEntity actedMovie = new MovieEntity();
        List<ActorEntity> cast = new ArrayList<>();
        List<MovieEntity> actedMovies = new ArrayList<>();
        cast.add(actor);
        actedMovies.add(actedMovie);
        actedMovie.setCast(cast);
        actor.setMoviesActed(actedMovies);

        when(this.actorRepository.existsById(1)).thenReturn(true);
        when(this.actorRepository.getById(1)).thenReturn(actor);
        this.actorService.deleteActor(1);

        verify(this.actorRepository, times(1)).getById(1);
        verify(this.actorRepository, times(1)).deleteById(1);
    }

}
