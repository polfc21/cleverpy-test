package com.cleverpy.data.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cleverpy_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String username;

    @NonNull
    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean active;

}
