package com.cleverpy.api.dtos;

import com.cleverpy.data.entities.ActorEntity;
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
public class ActorDTO {

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
    
    public ActorDTO(ActorEntity actor) {
        this.id = actor.getId();
        this.name = actor.getName();
        this.surname = actor.getSurname();
        this.country = actor.getCountry();
        this.age = actor.getAge();
        this.gender = actor.getGenderType().name();
    }

    public ActorEntity toActorEntity() {
        return new ActorEntity(this.name, this.surname, this.country, this.age, this.gender.toUpperCase());
    }

}
