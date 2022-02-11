package com.cleverpy.services;

import com.cleverpy.dtos.ActorDTO;
import com.cleverpy.entities.ActorEntity;
import com.cleverpy.entities.GenderType;
import com.cleverpy.exceptions.GenderException;
import com.cleverpy.exceptions.NotFoundException;
import com.cleverpy.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<ActorDTO> getAllActors() {
        List<ActorDTO> actorsDTO = this.actorRepository.findAll()
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        if (actorsDTO.isEmpty()) {
            throw new NotFoundException("No actors found in the database.");
        }
        return actorsDTO;
    }

    @Override
    public List<ActorDTO> getActorsByAge(Integer age) {
        List<ActorDTO> actorsDTO = this.actorRepository.getActorEntitiesByAge(age)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        if (actorsDTO.isEmpty()) {
            throw new NotFoundException("No actors found with " + age + " age.");
        }
        return actorsDTO;
    }

    @Override
    public List<ActorDTO> getActorsByCountry(String country) {
        List<ActorDTO> actorsDTO = this.actorRepository.getActorEntitiesByCountry(country)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        if (actorsDTO.isEmpty()) {
            throw new NotFoundException("No actors found with " + country + " country.");
        }
        return actorsDTO;
    }

    @Override
    public List<ActorDTO> getActorsByGender(String gender) {
        if (this.existsGender(gender)) {
            List<ActorDTO> actorsDTO =
                    this.actorRepository.getActorEntitiesByGenderType(this.getGenderType(gender))
                    .stream()
                    .map(ActorDTO::new)
                    .collect(Collectors.toList());
            if (actorsDTO.isEmpty()) {
                throw new NotFoundException("No actors found with " + gender + " gender.");
            }
            return actorsDTO;
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
    public ActorDTO getActorByNameAndSurname(String name, String surname) {
        ActorEntity actorEntity = this.actorRepository.getActorEntityByNameAndSurname(name, surname);
        if (actorEntity == null) {
            throw new NotFoundException("No actor found with " + name + " name and " + surname + " surname");
        }
        return new ActorDTO(actorEntity);
    }

    @Override
    public ActorDTO createActor(ActorDTO actorDTO) {
        ActorEntity actorEntity = this.actorRepository.save(actorDTO.toActorEntity());
        return new ActorDTO(actorEntity);
    }

    @Override
    public ActorDTO updateActor(Integer id, ActorDTO actorDTO) {
        Optional<ActorEntity> optionalActor = this.actorRepository.findById(id);
        if (optionalActor.isPresent()) {
            ActorEntity actorEntity = this.actorRepository.getById(id);
            actorEntity.setName(actorDTO.getName());
            actorEntity.setSurname(actorDTO.getSurname());
            actorEntity.setCountry(actorDTO.getCountry());
            actorEntity.setAge(actorDTO.getAge());
            this.actorRepository.save(actorEntity);
            return new ActorDTO(actorEntity);
        }
        throw new NotFoundException("No actor found with " + id + " id");
    }

    @Override
    @Transactional
    public void deleteActor(Integer id) {
        if (!this.actorRepository.existsById(id)) {
            throw new NotFoundException("No actor found with " + id + " id");
        }
        this.actorRepository.deleteById(id);
    }
}
