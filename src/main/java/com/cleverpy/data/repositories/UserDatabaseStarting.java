package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.Role;
import com.cleverpy.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatabaseStarting {

    private static final String ADMIN_NAME = "Administrator";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static final String CUSTOMER_NAME = "Customer";
    private static final String CUSTOMER_USERNAME = "customer";
    private static final String CUSTOMER_PASSWORD = "customer";

    private final UserRepository userRepository;

    @Autowired
    public UserDatabaseStarting(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.initialize();
    }

    void initialize() {
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
