package com.cleverpy.data.entities;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenderTypeTest {

    @Test
    void testGivenExistentGenderWhenExistsGenderThenReturnTrue() {
        assertThat(GenderType.existsGender("male"), is(true));
        assertThat(GenderType.existsGender("female"), is(true));
    }

    @Test
    void testGivenNotExistentGenderWhenExistsGenderThenReturnFalse() {
        assertThat(GenderType.existsGender("null"), is(false));
    }
}
