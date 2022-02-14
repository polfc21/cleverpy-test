package com.cleverpy.domain.services;

import com.cleverpy.data.entities.DirectorEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectorService {

    List<DirectorEntity> getAllDirectors(Pageable pageable);
    List<DirectorEntity> getDirectorsByAge(Integer age);
    List<DirectorEntity> getDirectorsByCountry(String country);
    List<DirectorEntity> getDirectorsByGender(String gender);
    DirectorEntity getDirectorByNameAndSurname(String name, String surname);
    DirectorEntity getDirectorById(Integer id);
    DirectorEntity createDirector(DirectorEntity director);
    DirectorEntity updateDirector(Integer id, DirectorEntity director);
    void deleteDirector(Integer id);

}
