package com.cleverpy.services;

import com.cleverpy.dtos.ActorDTO;
import com.cleverpy.entities.ActorEntity;
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
        return this.actorRepository.findAll()
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActorDTO> getActorsByAge(Integer age) {
        return this.actorRepository.getActorEntitiesByAge(age)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActorDTO> getActorsByCountry(String country) {
        return this.actorRepository.getActorEntitiesByCountry(country)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ActorDTO getActorByNameAndSurname(String name, String surname) {
        ActorEntity actorEntity = this.actorRepository.getActorEntityByNameAndSurname(name, surname);
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
        return null;
    }

    @Override
    @Transactional
    public void deleteActor(Integer id) {
        if (this.actorRepository.existsById(id)) {
            this.actorRepository.deleteById(id);
        }
    }
}
