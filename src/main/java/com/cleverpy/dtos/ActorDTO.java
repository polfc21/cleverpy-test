package com.cleverpy.dtos;

import com.cleverpy.entities.ActorEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
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

    @NonNull
    @Positive
    private Integer age;
    
    public ActorDTO(ActorEntity actor) {
        this.id = actor.getId();
        this.name = actor.getName();
        this.surname = actor.getSurname();
        this.country = actor.getCountry();
        this.age = actor.getAge();
    }

    public ActorEntity toActorEntity() {
        return new ActorEntity(this.name, this.surname, this.country, this.age);
    }

}
