package com.cleverpy.dtos;

import com.cleverpy.entities.DirectorEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDTO {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String country;

    @NonNull
    @Positive
    private Integer age;

    public DirectorDTO(DirectorEntity director) {
        this.id = director.getId();
        this.name = director.getName();
        this.surname = director.getSurname();
        this.country = director.getCountry();
        this.age = director.getAge();
    }

    public DirectorEntity toDirectorEntity() {
        return new DirectorEntity(this.name, this.surname, this.country, this.age);
    }

}
