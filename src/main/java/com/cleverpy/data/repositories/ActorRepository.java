package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.data.entities.GenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {

    List<ActorEntity> getActorEntitiesByAge(Integer age);
    List<ActorEntity> getActorEntitiesByCountry(String country);
    List<ActorEntity> getActorEntitiesByGenderType(GenderType genderType);
    ActorEntity getActorEntityByNameAndSurname(String name, String surname);

}
