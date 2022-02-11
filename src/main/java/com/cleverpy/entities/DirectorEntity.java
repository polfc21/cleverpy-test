package com.cleverpy.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "directors")
public class DirectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String country;

    @NonNull
    private Integer age;

    @Enumerated(EnumType.STRING)
    @NonNull
    private GenderType genderType;

    @OneToMany(mappedBy = "director")
    private List<MovieEntity> moviesDirected;

    public DirectorEntity(String name, String surname, String country, Integer age, String gender) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.age = age;
        this.genderType = GenderType.valueOf(gender);
    }

}
