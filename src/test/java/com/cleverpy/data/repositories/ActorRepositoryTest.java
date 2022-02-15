package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.data.entities.GenderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGivenActor21yearsWhenGetActorsByAge40ThenReturnListOfSizeZero() {
        ActorEntity actor = new ActorEntity();
        actor.setAge(21);
        this.testEntityManager.persist(actor);

        List<ActorEntity> actors = this.actorRepository.getActorEntitiesByAge(40);

        assertThat(actors.size(), is(0));
    }

    @Test
    void testGivenActorOfUnitedStatesWhenGetActorsByCountryUnitedStatesThenReturnListOfSizeOne() {
        ActorEntity actor = new ActorEntity();
        actor.setCountry("United States");
        this.testEntityManager.persist(actor);

        List<ActorEntity> actors = this.actorRepository.getActorEntitiesByCountry("United States");

        assertThat(actors.size(), is(1));
    }

    @Test
    void testGivenActorMaleWhenGetActorsByGenderFemaleThenReturnListOfSizeZero() {
        ActorEntity actor = new ActorEntity();
        actor.setGenderType(GenderType.MALE);
        this.testEntityManager.persist(actor);

        List<ActorEntity> actors = this.actorRepository.getActorEntitiesByGenderType(GenderType.FEMALE);

        assertThat(actors.size(), is(0));
    }

    @Test
    void testGivenActorAlNamePacinoSurnameWhenGetActorByNameAndSurnameRobertDeNiroThenReturnNull() {
        ActorEntity alPacino = new ActorEntity();
        alPacino.setName("Al");
        alPacino.setSurname("Pacino");
        this.testEntityManager.persist(alPacino);

        ActorEntity robertDeNiro =
                this.actorRepository.getActorEntityByNameAndSurname("Robert", "De Niro");

        Assertions.assertNull(robertDeNiro);
    }

}
