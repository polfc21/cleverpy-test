package com.cleverpy.domain.services;

import com.cleverpy.data.entities.Role;
import com.cleverpy.data.entities.UserEntity;
import com.cleverpy.data.repositories.UserRepository;
import com.cleverpy.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String ADMIN_NAME = "Administrator";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static final String CUSTOMER_NAME = "Customer";
    private static final String CUSTOMER_USERNAME = "customer";
    private static final String CUSTOMER_PASSWORD = "customer";

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.initialize();
    }

    public String login(String username) {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The username don't exist: " + username));
        return this.jwtService.createToken(user.getUsername(), user.getName(), user.getRole().name());
    }

    private void initialize() {
        UserEntity admin = UserEntity.builder()
                .username(ADMIN_USERNAME)
                .name(ADMIN_NAME)
                .password(new BCryptPasswordEncoder().encode(ADMIN_PASSWORD))
                .role(Role.ADMIN)
                .build();
        UserEntity customer = UserEntity.builder()
                .username(CUSTOMER_USERNAME)
                .name(CUSTOMER_NAME)
                .password(new BCryptPasswordEncoder().encode(CUSTOMER_PASSWORD))
                .role(Role.CUSTOMER)
                .build();
        this.userRepository.save(admin);
        this.userRepository.save(customer);
    }

}
