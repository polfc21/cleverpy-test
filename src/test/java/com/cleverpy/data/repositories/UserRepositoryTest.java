package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGivenNoUserSavedWhenFindByUsernameThenReturnEmpty() {
        assertThat(this.userRepository.findByUsername("no username"), is(Optional.empty()));
    }

    @Test
    void testGivenUserSavedWhenFindByUsernameThenReturnOptional() {
        UserEntity user = new UserEntity();
        user.setUsername("admin");

        this.testEntityManager.persist(user);

        assertThat(this.userRepository.findByUsername("admin"), is(Optional.of(user)));
    }
}
