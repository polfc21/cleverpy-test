package com.cleverpy.domain.services;

import com.cleverpy.domain.exceptions.GenderException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.domain.exceptions.ParentRowException;
import com.cleverpy.data.entities.DirectorEntity;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.data.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<DirectorEntity> getAllDirectors() {
        List<DirectorEntity> directors = this.directorRepository.findAll();
        if (directors.isEmpty()) {
            throw new NotFoundException("No directors found in the database.");
        }
        return directors;
    }

    @Override
    public List<DirectorEntity> getDirectorsByAge(Integer age) {
        List<DirectorEntity> directors = this.directorRepository.getDirectorEntitiesByAge(age);
        if (directors.isEmpty()) {
            throw new NotFoundException("No directors found with " + age + " age.");
        }
        return directors;
    }

    @Override
    public List<DirectorEntity> getDirectorsByCountry(String country) {
        List<DirectorEntity> directors = this.directorRepository.getDirectorEntitiesByCountry(country);
        if (directors.isEmpty()) {
            throw new NotFoundException("No directors found with " + country + " country.");
        }
        return directors;
    }

    @Override
    public List<DirectorEntity> getDirectorsByGender(String gender) {
        if (this.existsGender(gender)) {
            List<DirectorEntity> directors =
                    this.directorRepository.getDirectorEntitiesByGenderType(this.getGenderType(gender));
            if (directors.isEmpty()) {
                throw new NotFoundException("No directors found with " + gender + " gender.");
            }
            return directors;
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
    public DirectorEntity getDirectorByNameAndSurname(String name, String surname) {
        DirectorEntity director = this.directorRepository.getDirectorEntityByNameAndSurname(name, surname);
        if (director == null) {
            throw new NotFoundException("No director found with " + name + " name and " + surname + " surname");
        }
        return director;
    }

    @Override
    public DirectorEntity getDirectorById(Integer id) {
        if (this.directorRepository.existsById(id)) {
            return this.directorRepository.getById(id);
        }
        throw new NotFoundException("No director saved with " + id + " id");
    }

    @Override
    public DirectorEntity createDirector(DirectorEntity director) {
        return this.directorRepository.save(director);
    }

    @Override
    public DirectorEntity updateDirector(Integer id, DirectorEntity director) {
        Optional<DirectorEntity> optionalDirector = this.directorRepository.findById(id);
        if (optionalDirector.isPresent()) {
            DirectorEntity updatedDirectorEntity = this.getUpdatedDirector(id, director);
            return this.directorRepository.save(updatedDirectorEntity);
        }
        throw new NotFoundException("No director found with " + id + " id");
    }

    private DirectorEntity getUpdatedDirector(Integer id, DirectorEntity director) {
        DirectorEntity updatedDirector = this.directorRepository.getById(id);
        updatedDirector.setName(director.getName());
        updatedDirector.setSurname(director.getSurname());
        updatedDirector.setCountry(director.getCountry());
        updatedDirector.setAge(director.getAge());
        updatedDirector.setGenderType((director.getGenderType()));
        return updatedDirector;
    }

    @Override
    public void deleteDirector(Integer id) {
        Optional<DirectorEntity> optionalDirectorEntity = this.directorRepository.findById(id);
        if (optionalDirectorEntity.isPresent()) {
            if (this.hasMoviesDirected(id)) {
                throw new ParentRowException("The director has movies directed. Remove first all movies of this director");
            }
            this.directorRepository.deleteById(id);
        } else {
            throw new NotFoundException("No director found with " + id + " id");
        }
    }

    private boolean hasMoviesDirected(Integer id) {
        return this.directorRepository.getNumberMoviesByDirector(id) > 0;
    }
}
