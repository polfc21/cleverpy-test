package com.cleverpy.domain.services;

import com.cleverpy.data.entities.ActorEntity;

import java.util.List;

public interface ActorService {

    List<ActorEntity> getAllActors();
    List<ActorEntity> getActorsByAge(Integer age);
    List<ActorEntity> getActorsByCountry(String country);
    List<ActorEntity> getActorsByGender(String gender);
    ActorEntity getActorByNameAndSurname(String name, String surname);
    ActorEntity createActor(ActorEntity actor);
    ActorEntity updateActor(Integer id, ActorEntity actor);
    void deleteActor(Integer id);

}
