package com.cleverpy.controllers;

import com.cleverpy.dtos.ActorDTO;
import com.cleverpy.services.ActorService;
import com.cleverpy.validators.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(ActorController.ACTORS)
public class ActorController {

    public static final String ACTORS = "/actors";
    public static final String AGE = "/age/{age}";
    public static final String COUNTRY = "/country/{country}";
    public static final String GENDER = "/gender/{gender}";
    public static final String NAME = "/name/{name}";
    public static final String SURNAME = "/surname/{surname}";
    public static final String ID = "/{id}";

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<List<ActorDTO>> getAllActors() {
        List<ActorDTO> actorsDTO = this.actorService.getAllActors();
        if (actorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.AGE)
    public ResponseEntity<List<ActorDTO>> getActorsByAge(@PathVariable @Positive Integer age) {
        List<ActorDTO> actorsDTO = this.actorService.getActorsByAge(age);
        if (actorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.COUNTRY)
    public ResponseEntity<List<ActorDTO>> getActorsByCountry(@PathVariable @NotBlank String country) {
        List<ActorDTO> actorsDTO = this.actorService.getActorsByCountry(country);
        if (actorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.GENDER)
    public ResponseEntity<List<ActorDTO>> getActorsByGender(@PathVariable @Gender String gender) {
        List<ActorDTO> actorsDTO = this.actorService.getActorsByGender(gender);
        if (actorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.NAME + ActorController.SURNAME)
    public ResponseEntity<ActorDTO> getActorsByNameAndSurname(@PathVariable @NotBlank String name,
                                                              @PathVariable @NotBlank String surname) {
        ActorDTO actorDTO = this.actorService.getActorByNameAndSurname(name, surname);
        if (actorDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActorDTO> createDirector(@Valid @RequestBody ActorDTO actorDTO) {
        ActorDTO actorDTOCreated = this.actorService.createActor(actorDTO);
        return new ResponseEntity<>(actorDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping(ActorController.ID)
    public ResponseEntity<ActorDTO> updateDirector(@PathVariable Integer id, @Valid @RequestBody ActorDTO actorDTO) {
        ActorDTO actorDTOUpdated = this.actorService.updateActor(id, actorDTO);
        if (actorDTOUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actorDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping(ActorController.ID)
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable Integer id) {
        this.actorService.deleteActor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
