package com.cleverpy.api.dtos;

import com.cleverpy.data.entities.ActorEntity;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ActorDTOTest {

    @Test
    void testGivenActorDTOWhenToActorEntityThenNewActorDTOWithActorEntityDataIsActorDTO() {
        ActorDTO actorDTO = new ActorDTO(null, "Pol", "Farreny",
                "Spain", 24, "MALE");

        ActorEntity actor = actorDTO.toActorEntity();

        assertThat(actorDTO, is(new ActorDTO(actor)));
    }
}
