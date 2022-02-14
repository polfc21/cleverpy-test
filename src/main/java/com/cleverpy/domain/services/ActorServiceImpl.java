package com.cleverpy.domain.services;

import com.cleverpy.domain.exceptions.GenderException;
import com.cleverpy.domain.exceptions.NotFoundException;
import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.data.entities.GenderType;
import com.cleverpy.data.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<ActorEntity> getAllActors(Pageable pageable) {
        Page<ActorEntity> actors = this.actorRepository.findAll(pageable);
        if (actors.isEmpty()) {
            throw new NotFoundException("No actors found in the database.");
        }
        return actors.getContent();
    }

    @Override
    public List<ActorEntity> getActorsByAge(Integer age) {
        List<ActorEntity> actors = this.actorRepository.getActorEntitiesByAge(age);
        if (actors.isEmpty()) {
            throw new NotFoundException("No actors found with " + age + " age.");
        }
        return actors;
    }

    @Override
    public List<ActorEntity> getActorsByCountry(String country) {
        List<ActorEntity> actors = this.actorRepository.getActorEntitiesByCountry(country);
        if (actors.isEmpty()) {
            throw new NotFoundException("No actors found with " + country + " country.");
        }
        return actors;
    }

    @Override
    public List<ActorEntity> getActorsByGender(String gender) {
        if (this.existsGender(gender)) {
            List<ActorEntity> actors = this.actorRepository.getActorEntitiesByGenderType(this.getGenderType(gender));
            if (actors.isEmpty()) {
                throw new NotFoundException("No actors found with " + gender + " gender.");
            }
            return actors;
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
    public ActorEntity getActorByNameAndSurname(String name, String surname) {
        ActorEntity actor = this.actorRepository.getActorEntityByNameAndSurname(name, surname);
        if (actor == null) {
            throw new NotFoundException("No actor found with " + name + " name and " + surname + " surname");
        }
        return actor;
    }

    @Override
    public ActorEntity createActor(ActorEntity actor) {
        return this.actorRepository.save(actor);
    }

    @Override
    public ActorEntity updateActor(Integer id, ActorEntity actor) {
        Optional<ActorEntity> optionalActor = this.actorRepository.findById(id);
        if (optionalActor.isPresent()) {
            ActorEntity updatedActor = this.getUpdatedActor(id, actor);
            this.actorRepository.save(updatedActor);
            return updatedActor;
        }
        throw new NotFoundException("No actor found with " + id + " id");
    }

    private ActorEntity getUpdatedActor(Integer id, ActorEntity actor) {
        ActorEntity updatedActor = this.actorRepository.getById(id);
        updatedActor.setName(actor.getName());
        updatedActor.setSurname(actor.getSurname());
        updatedActor.setCountry(actor.getCountry());
        updatedActor.setAge(actor.getAge());
        updatedActor.setGenderType(actor.getGenderType());
        return updatedActor;
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
