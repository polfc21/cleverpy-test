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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGivenNotExistentUsernameWhenLoadUserByUsernameThenThrowUsernameNotFoundException() {
        String username = "notExistent";

        when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> this.userDetailsService.loadUserByUsername(username));
    }

    @Test
    void testGivenExistentUsernameWhenLoadUserByUsernameThenReturnUserDetails() {
        String username = "existent";
        UserEntity user = new UserEntity(1, username,"name","password", Role.ADMIN, true);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().withPrefix()));
        User userDetails = new User(user.getUsername(), user.getPassword(), user.getActive(),
                true, true, true, authorities);

        when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertThat(this.userDetailsService.loadUserByUsername(username), is(userDetails));
    }
}
