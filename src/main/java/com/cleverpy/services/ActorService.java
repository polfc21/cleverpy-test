package com.cleverpy.services;

import com.cleverpy.dtos.ActorDTO;

import java.util.List;

public interface ActorService {

    List<ActorDTO> getAllActors();
    List<ActorDTO> getActorsByAge(Integer age);
    List<ActorDTO> getActorsByCountry(String country);
    ActorDTO getActorByNameAndSurname(String name, String surname);
    ActorDTO createActor(ActorDTO actorDTO);
    ActorDTO updateActor(Integer id, ActorDTO actorDTO);
    void deleteActor(Integer id);

}
