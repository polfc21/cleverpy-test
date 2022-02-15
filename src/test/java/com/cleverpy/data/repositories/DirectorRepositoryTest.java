package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.GenderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGivenDirector77yearsWhenGetDirectorsByAge77ThenReturnListOfSizeOne() {
        DirectorEntity director = new DirectorEntity();
        director.setAge(77);
        this.testEntityManager.persist(director);

        List<DirectorEntity> directors = this.directorRepository.getDirectorEntitiesByAge(77);

        assertThat(directors.size(), is(1));
    }

    @Test
    void testGivenDirectorOfSpainWhenGetDirectorsByCountryItalyThenReturnListOfSizeZero() {
        DirectorEntity director = new DirectorEntity();
        director.setCountry("Spain");
        this.testEntityManager.persist(director);

        List<DirectorEntity> directors = this.directorRepository.getDirectorEntitiesByCountry("Italy");

        assertThat(directors.size(), is(0));
    }

    @Test
    void testGivenDirectorFemaleWhenGetDirectorsByGenderFemaleThenReturnListOfSizeOne() {
        DirectorEntity director = new DirectorEntity();
        director.setGenderType(GenderType.FEMALE);
        this.testEntityManager.persist(director);

        List<DirectorEntity> directors =
                this.directorRepository.getDirectorEntitiesByGenderType(GenderType.FEMALE);

        assertThat(directors.size(), is(1));
    }

    @Test
    void testGivenDirectorQuentinNameTarantinoSurnameWhenGetDirectorByNameAndSurnameQuentinTarantinoThenReturnQuentinTarantino() {
        DirectorEntity quentinTarantino = new DirectorEntity();
        quentinTarantino.setName("Quentin");
        quentinTarantino.setSurname("Tarantino");
        this.testEntityManager.persist(quentinTarantino);

        DirectorEntity director =
                this.directorRepository.getDirectorEntityByNameAndSurname("Quentin", "Tarantino");

        assertThat(director , is(quentinTarantino));
    }

    @Test
    void testGivenDirectorWithZeroMoviesWhenGetNumberMoviesByDirectorIdThenReturnZero() {
        DirectorEntity director = new DirectorEntity();
        this.testEntityManager.persist(director);
        int id = director.getId();

        int numberMovies = this.directorRepository.getNumberMoviesByDirector(id);

        assertThat(numberMovies , is(0));
    }
}
