package com.cleverpy.domain.services;

import com.cleverpy.data.entities.UserEntity;
import com.cleverpy.data.repositories.UserRepository;
import com.cleverpy.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String login(String username) {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The username don't exist: " + username));
        return this.jwtService.createToken(user.getUsername(), user.getName(), user.getRole().name());
    }

}
