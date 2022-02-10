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
@Entity(name = "actors")
public class ActorEntity {

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

    @ManyToMany(mappedBy = "cast")
    private List<MovieEntity> moviesActed;

    public ActorEntity(String name, String surname, String country, Integer age) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.age = age;
    }
}
