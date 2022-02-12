package com.cleverpy.domain.services;

import com.cleverpy.data.entities.DirectorEntity;

import java.util.List;

public interface DirectorService {

    List<DirectorEntity> getAllDirectors();
    List<DirectorEntity> getDirectorsByAge(Integer age);
    List<DirectorEntity> getDirectorsByCountry(String country);
    List<DirectorEntity> getDirectorsByGender(String gender);
    DirectorEntity getDirectorByNameAndSurname(String name, String surname);
    DirectorEntity getDirectorById(Integer id);
    DirectorEntity createDirector(DirectorEntity director);
    DirectorEntity updateDirector(Integer id, DirectorEntity director);
    void deleteDirector(Integer id);

}
