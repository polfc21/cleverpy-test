package com.cleverpy.data.repositories;

import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.GenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorEntity, Integer> {

    List<DirectorEntity> getDirectorEntitiesByAge(Integer age);
    List<DirectorEntity> getDirectorEntitiesByCountry(String country);
    List<DirectorEntity> getDirectorEntitiesByGenderType(GenderType genderType);
    DirectorEntity getDirectorEntityByNameAndSurname(String name, String surname);
    @Query(value = "SELECT COUNT(*) FROM movies WHERE director_id = ?1", nativeQuery = true)
    int getNumberMoviesByDirector(Integer id);

}
