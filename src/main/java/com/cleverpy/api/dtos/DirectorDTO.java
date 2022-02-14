package com.cleverpy.api.dtos;

import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.domain.validators.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Positive
    private Integer age;

    @NotNull
    @Gender
    private String gender;

    public DirectorDTO(DirectorEntity director) {
        this.id = director.getId();
        this.name = director.getName();
        this.surname = director.getSurname();
        this.country = director.getCountry();
        this.age = director.getAge();
        this.gender = director.getGenderType().name();
    }

    public DirectorEntity toDirectorEntity() {
        return new DirectorEntity(this.name, this.surname, this.country, this.age, this.gender.toUpperCase());
    }

}
