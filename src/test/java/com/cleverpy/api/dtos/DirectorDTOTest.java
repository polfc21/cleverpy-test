package com.cleverpy.api.dtos;

import com.cleverpy.data.entities.DirectorEntity;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DirectorDTOTest {

    @Test
    void testGivenDirectorDTOWhenToDirectorEntityThenNewDirectorDTOWithDirectorEntityDataIsDirectorDTO() {
        DirectorDTO directorDTO = new DirectorDTO(null, "Pol", "Farreny",
                "Spain", 24, "MALE");

        DirectorEntity director = directorDTO.toDirectorEntity();

        assertThat(directorDTO, is(new DirectorDTO(director)));
    }
}
