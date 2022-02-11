package com.cleverpy.services;

import com.cleverpy.dtos.DirectorDTO;
import com.cleverpy.entities.DirectorEntity;
import com.cleverpy.entities.GenderType;
import com.cleverpy.exceptions.GenderException;
import com.cleverpy.exceptions.NotFoundException;
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
        List<DirectorDTO> directorsDTO = this.directorRepository.findAll()
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        if (directorsDTO.isEmpty()) {
            throw new NotFoundException("No directors found in the database.");
        }
        return directorsDTO;
    }

    @Override
    public List<DirectorDTO> getDirectorsByAge(Integer age) {
        List<DirectorDTO> directorsDTO = this.directorRepository.getDirectorEntitiesByAge(age)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        if (directorsDTO.isEmpty()) {
            throw new NotFoundException("No directors found with " + age + " age.");
        }
        return directorsDTO;
    }

    @Override
    public List<DirectorDTO> getDirectorsByCountry(String country) {
        List<DirectorDTO> directorsDTO = this.directorRepository.getDirectorEntitiesByCountry(country)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        if (directorsDTO.isEmpty()) {
            throw new NotFoundException("No directors found with " + country + " country.");
        }
        return directorsDTO;
    }

    @Override
    public List<DirectorDTO> getDirectorsByGender(String gender) {
        if (this.existsGender(gender)) {
            List<DirectorDTO> directorsDTO =
                    this.directorRepository.getDirectorEntitiesByGenderType(this.getGenderType(gender))
                            .stream()
                            .map(DirectorDTO::new)
                            .collect(Collectors.toList());
            if (directorsDTO.isEmpty()) {
                throw new NotFoundException("No directors found with " + gender + " gender.");
            }
            return directorsDTO;
        }
        throw new GenderException("Gender " + gender + " no registered in the database.");
    }

    private boolean existsGender(String gender) {
        return GenderType.existsGender(gender.toUpperCase());
    }

    private GenderType getGenderType(String gender) {
        return GenderType.valueOf(gender.toUpperCase());
    }

    @Override
    public DirectorDTO getDirectorByNameAndSurname(String name, String surname) {
        DirectorEntity directorEntity = this.directorRepository.getDirectorEntityByNameAndSurname(name, surname);
        if (directorEntity == null) {
            throw new NotFoundException("No director found with " + name + " name and " + surname + " surname");
        }
        return new DirectorDTO(directorEntity);
    }

    @Override
    public DirectorEntity getDirectorById(Integer id) {
        if (this.directorRepository.existsById(id)) {
            return this.directorRepository.getById(id);
        }
        throw new NotFoundException("No director saved with " + id + " id");
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
        throw new NotFoundException("No director found with " + id + " id");
    }

    @Override
    public void deleteDirector(Integer id) {
        if (!this.directorRepository.existsById(id)) {
            throw new NotFoundException("No director found with " + id + " id");
        }
        this.directorRepository.deleteById(id);
    }
}
