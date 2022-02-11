package com.cleverpy.services;

import com.cleverpy.dtos.DirectorDTO;
import com.cleverpy.entities.DirectorEntity;
import com.cleverpy.entities.GenderType;
import com.cleverpy.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<DirectorDTO> getAllDirectors() {
        return this.directorRepository.findAll()
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorDTO> getDirectorsByAge(Integer age) {
        return this.directorRepository.getDirectorEntitiesByAge(age)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorDTO> getDirectorsByCountry(String country) {
        return this.directorRepository.getDirectorEntitiesByCountry(country)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorDTO> getDirectorsByGender(String gender) {
        GenderType genderType = GenderType.valueOf(gender.toUpperCase());
        return this.directorRepository.getDirectorEntitiesByGenderType(genderType)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public DirectorDTO getDirectorByNameAndSurname(String name, String surname) {
        DirectorEntity directorEntity = this.directorRepository.getDirectorEntityByNameAndSurname(name, surname);
        return new DirectorDTO(directorEntity);
    }

    @Override
    public DirectorDTO createDirector(DirectorDTO directorDTO) {
        DirectorEntity directorEntity = this.directorRepository.save(directorDTO.toDirectorEntity());
        return new DirectorDTO(directorEntity);
    }

    @Override
    public DirectorDTO updateDirector(Integer id, DirectorDTO directorDTO) {
        Optional<DirectorEntity> optionalDirector = this.directorRepository.findById(id);
        if (optionalDirector.isPresent()) {
            DirectorEntity directorEntity = this.directorRepository.getById(id);
            directorEntity.setName(directorDTO.getName());
            directorEntity.setSurname(directorDTO.getSurname());
            directorEntity.setCountry(directorDTO.getCountry());
            directorEntity.setAge(directorDTO.getAge());
            this.directorRepository.save(directorEntity);
            return new DirectorDTO(directorEntity);
        }
        return null;
    }

    @Override
    public void deleteDirector(Integer id) {
        if (this.directorRepository.existsById(id)) {
            this.directorRepository.deleteById(id);
        }
    }
}
