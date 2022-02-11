package com.cleverpy.repositories;

import com.cleverpy.entities.DirectorEntity;
import com.cleverpy.entities.GenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorEntity, Integer> {

    List<DirectorEntity> getDirectorEntitiesByAge(Integer age);
    List<DirectorEntity> getDirectorEntitiesByCountry(String country);
    List<DirectorEntity> getDirectorEntitiesByGenderType(GenderType genderType);
    DirectorEntity getDirectorEntityByNameAndSurname(String name, String surname);

}
