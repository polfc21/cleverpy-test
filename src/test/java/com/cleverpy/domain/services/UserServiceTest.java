package com.cleverpy.domain.services;

import com.cleverpy.data.entities.Role;
import com.cleverpy.data.entities.UserEntity;
import com.cleverpy.data.repositories.UserRepository;
import com.cleverpy.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Test
    void testGivenNotExistentUsernameWhenLoginThenThrowNotFoundException() {
        String username = "notExistent";

        when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.userService.login(username));
    }

    @Test
    void testGivenExistentUsernameWhenLoginThenReturnStringToken() {
        String username = "existent";
        UserEntity user = new UserEntity(1, username,"name","password", Role.ADMIN, true);
        String token = "token";

        when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(this.jwtService.createToken(user.getUsername(), user.getName(), user.getRole().name())).thenReturn(token);

        assertThat(this.userService.login(username), is(token));
    }

}
