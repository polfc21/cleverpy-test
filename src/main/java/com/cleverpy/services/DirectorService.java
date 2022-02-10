package com.cleverpy.services;

import com.cleverpy.dtos.DirectorDTO;

import java.util.List;

public interface DirectorService {

    List<DirectorDTO> getAllDirectors();
    List<DirectorDTO> getDirectorsByAge(Integer age);
    List<DirectorDTO> getDirectorsByCountry(String country);
    DirectorDTO getDirectorByNameAndSurname(String name, String surname);
    DirectorDTO createDirector(DirectorDTO directorDTO);
    DirectorDTO updateDirector(DirectorDTO directorDTO);
    void deleteDirector(Integer id);

}
