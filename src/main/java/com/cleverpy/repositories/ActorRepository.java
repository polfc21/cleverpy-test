package com.cleverpy.repositories;

import com.cleverpy.entities.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {

    List<ActorEntity> getActorEntitiesByAge(Integer age);
    List<ActorEntity> getActorEntitiesByCountry(String country);
    ActorEntity getActorEntityByNameAndSurname(String name, String surname);

}
