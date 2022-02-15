package com.cleverpy.domain.services;

import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.data.repositories.DirectorRepository;
import com.cleverpy.domain.exceptions.GenderException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.domain.exceptions.ParentRowException;
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
public class DirectorServiceImplTest {

    @InjectMocks
    private DirectorServiceImpl directorService;

    @Mock
    private DirectorRepository directorRepository;

    @Test
    void testGivenNonSavedDirectorsWhenGetAllDirectorsThenThrowNotFoundException() {
        Pageable pageable = PageRequest.of(0,2);

        when(this.directorRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(NotFoundException.class, () -> this.directorService.getAllDirectors(pageable));
    }

    @Test
    void testGivenSavedDirectorsWhenGetAllDirectorsThenReturnDirectors() {
        Pageable pageable = PageRequest.of(0,1);
        DirectorEntity director = new DirectorEntity();
        List<DirectorEntity> directors = new ArrayList<>();
        directors.add(director);
        Page<DirectorEntity> pageDirectors = new PageImpl<>(directors);

        when(this.directorRepository.findAll(pageable)).thenReturn(pageDirectors);

        assertThat(this.directorService.getAllDirectors(pageable), is(directors));
    }

    @Test
    void testGivenNonSavedDirectorsOf70AgeWhenGetDirectorsByAge70ThenThrowNotFoundException() {
        List<DirectorEntity> emptyList = new ArrayList<>();

        when(this.directorRepository.getDirectorEntitiesByAge(70)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.directorService.getDirectorsByAge(70));
    }

    @Test
    void testGivenSavedDirectorsOf60WhenGetDirectorsByAge60ThenReturnDirectors() {
        DirectorEntity director = new DirectorEntity();
        List<DirectorEntity> sixtyDirectors = new ArrayList<>();
        sixtyDirectors.add(director);

        when(this.directorRepository.getDirectorEntitiesByAge(60)).thenReturn(sixtyDirectors);

        assertThat(this.directorService.getDirectorsByAge(60), is(sixtyDirectors));
    }

    @Test
    void testGivenNonSavedDirectorsOfUnitedStatesWhenGetDirectorsByUnitedStatesCountryThenThrowNotFoundException() {
        List<DirectorEntity> emptyList = new ArrayList<>();

        when(this.directorRepository.getDirectorEntitiesByCountry("United States")).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.directorService.getDirectorsByCountry("United States"));
    }

    @Test
    void testGivenSavedDirectorsOfCubaWhenGetDirectorsByCubaCountryThenReturnDirectors() {
        DirectorEntity director = new DirectorEntity();
        List<DirectorEntity> cubanDirectors = new ArrayList<>();
        cubanDirectors.add(director);

        when(this.directorRepository.getDirectorEntitiesByCountry("Cuba")).thenReturn(cubanDirectors);

        assertThat(this.directorService.getDirectorsByCountry("Cuba"), is(cubanDirectors));
    }

    @Test
    void testGivenIncorrectGenderWhenGetDirectorsByGenderThenThrowGenderException() {
        String incorrectGender = "null";

        assertThrows(GenderException.class, () -> this.directorService.getDirectorsByGender(incorrectGender));
    }

    @Test
    void testGivenNonSavedDirectorsFemaleWhenGetDirectorsByFemaleGenderThenThrowNotFoundException() {
        List<DirectorEntity> emptyList = new ArrayList<>();

        when(this.directorRepository.getDirectorEntitiesByGenderType(GenderType.FEMALE)).thenReturn(emptyList);

        assertThrows(NotFoundException.class, () -> this.directorService.getDirectorsByGender("female"));
    }

    @Test
    void testGivenSavedDirectorsMaleWhenGetDirectorsByMaleGenderThenReturnDirectors() {
        DirectorEntity director = new DirectorEntity();
        List<DirectorEntity> maleDirectors = new ArrayList<>();
        maleDirectors.add(director);

        when(this.directorRepository.getDirectorEntitiesByGenderType(GenderType.MALE)).thenReturn(maleDirectors);

        assertThat(this.directorService.getDirectorsByGender("male"), is(maleDirectors));
    }

    @Test
    void testGivenNonSavedDirectorQuentinNameTarantinoSurnameWhenGetDirectorByNameAndSurnameQuentinTarantinoThenThrowNotFoundException() {
        when(this.directorRepository.getDirectorEntityByNameAndSurname("Quentin", "Tarantino")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.directorService.getDirectorByNameAndSurname("Quentin", "Tarantino"));
    }

    @Test
    void testGivenSavedDirectorPedroNameAlmodovarSurnameWhenGetDirectorByNameAndSurnamePedroAlmodovarThenReturnAlmodovar() {
        DirectorEntity almodovar = new DirectorEntity();

        when(this.directorRepository.getDirectorEntityByNameAndSurname("Pedro","Almodovar")).thenReturn(almodovar);

        assertThat(this.directorService.getDirectorByNameAndSurname("Pedro", "Almodovar"), is(almodovar));
    }

    @Test
    void testGivenNonSavedDirectorWith1IdWhenGetDirectorById1ThenThrowNotFoundException() {
        when(this.directorRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> this.directorService.getDirectorById(1));
    }

    @Test
    void testGivenSavedDirectorWith1IdWhenGetDirectorByIdThenReturnDirectorId1() {
        DirectorEntity directorIdOne = new DirectorEntity();

        when(this.directorRepository.existsById(1)).thenReturn(true);
        when(this.directorRepository.getById(1)).thenReturn(directorIdOne);

        assertThat(this.directorService.getDirectorById(1), is(directorIdOne));
    }

    @Test
    void testGivenDirectorWhenCreateDirectorThenReturnDirector() {
        DirectorEntity director = new DirectorEntity();

        when(this.directorRepository.save(director)).thenReturn(director);

        assertThat(this.directorService.createDirector(director), is(director));
    }

    @Test
    void testGivenNotPresentDirectorWith1IdWhenUpdateDirectorThenThrowNotFoundException() {
        when(this.directorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.directorService.updateDirector(1, null));
    }

    @Test
    void testGivenPresentDirectorWith1IdWhenUpdateDirectorThenReturnDirectorUpdated() {
        DirectorEntity director = new DirectorEntity("name","surname","country",70,"MALE");
        DirectorEntity updatedDirector = new DirectorEntity("name","surname","country",68,"MALE");

        when(this.directorRepository.findById(1)).thenReturn(Optional.of(director));
        when(this.directorRepository.getById(1)).thenReturn(updatedDirector);
        when(this.directorRepository.save(updatedDirector)).thenReturn(updatedDirector);

        assertThat(this.directorService.updateDirector(1, director), is(updatedDirector));
    }

    @Test
    void testGivenNotPresentDirectorWith1IdWhenDeleteDirectorThenThrowNotFoundException() {
        when(this.directorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.directorService.deleteDirector(1));
    }

    @Test
    void testGivenPresentDirectorWith1IdWithDirectedMoviesWhenDeleteDirectorThenThrowParentRowException() {
        DirectorEntity directorIdOne = new DirectorEntity();

        when(this.directorRepository.findById(1)).thenReturn(Optional.of(directorIdOne));
        when(this.directorRepository.getNumberMoviesByDirector(1)).thenReturn(1);

        assertThrows(ParentRowException.class, () -> this.directorService.deleteDirector(1));
    }

    @Test
    void testGivenPresentDirectorWith1IdWithoutDirectedMoviesWhenDeleteDirectorThenDirectorDeleted() {
        DirectorEntity directorIdOne = new DirectorEntity();

        when(this.directorRepository.findById(1)).thenReturn(Optional.of(directorIdOne));
        when(this.directorRepository.getNumberMoviesByDirector(1)).thenReturn(0);
        this.directorService.deleteDirector(1);

        verify(this.directorRepository, times(1)).deleteById(1);
    }
}
