package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.ActorDTO;
import com.cleverpy.data.entities.ActorEntity;
import com.cleverpy.domain.services.ActorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping(ActorController.ACTORS)
@Api(tags = "API Rest. Actors management.")
public class ActorController {

    public static final String ACTORS = "/actors";
    public static final String AGE = "/age/{age}";
    public static final String COUNTRY = "/country/{country}";
    public static final String GENDER = "/gender/{gender}";
    public static final String NAME = "/name/{name}";
    public static final String SURNAME = "/surname/{surname}";
    public static final String ID = "/{id}";

    public static final String OK_ACTOR_MESSAGE = "Response ok if the actor was found";
    public static final String OK_ACTORS_MESSAGE = "Response ok if the actors were found";
    public static final String OK_UPDATED_MESSAGE = "Response ok if the actor was updated";
    public static final String CREATED_MESSAGE = "Response created if the actor was created";
    public static final String NO_CONTENT_DELETED_MESSAGE = "Response no content if the actor was deleted";
    public static final String NOT_FOUND_ACTOR_MESSAGE = "Response not found if the actor doesn't exists";
    public static final String NOT_FOUND_ACTORS_MESSAGE = "Response not found if there aren't actors";
    public static final String BAD_REQUEST_GENDER_MESSAGE = "Response bad request if the gender is incorrect";
    public static final String BAD_REQUEST_INCORRECT_ACTOR_MESSAGE = "Response bad request if the actor provided is not correct";
    public static final String UNAUTHORIZED_MESSAGE = "Response access denied if the role is not granted for this method";

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    @ApiOperation(notes = "Retrieve all actors that are saved in the database", value = "Get all actors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ActorController.OK_ACTORS_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTORS_MESSAGE)
    })
    public ResponseEntity<List<ActorDTO>> getAllActors(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size) {

        List<ActorDTO> actorsDTO = this.actorService.getAllActors(PageRequest.of(page, size))
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.AGE)
    @ApiOperation(notes = "Retrieve all actors that match with age passed by path", value = "Get actors by age")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ActorController.OK_ACTORS_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTORS_MESSAGE)
    })
    public ResponseEntity<List<ActorDTO>> getActorsByAge(
            @ApiParam(example = "25", value = "Age", allowableValues = "25, 43, 67, 76, 39", required = true)
            @PathVariable Integer age) {
        List<ActorDTO> actorsDTO = this.actorService.getActorsByAge(age)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.COUNTRY)
    @ApiOperation(notes = "Retrieve all actors that match with country passed by path", value = "Get actors by country")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ActorController.OK_ACTORS_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTORS_MESSAGE)
    })
    public ResponseEntity<List<ActorDTO>> getActorsByCountry(
            @ApiParam(example = "Spain", value = "Country", allowableValues = "Spain, United States, Italy", required = true)
            @PathVariable String country) {
        List<ActorDTO> actorsDTO = this.actorService.getActorsByCountry(country)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.GENDER)
    @ApiOperation(notes = "Retrieve all actors that match with gender passed by path", value = "Get actors by gender")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ActorController.OK_ACTORS_MESSAGE),
            @ApiResponse(code = 400, message = ActorController.BAD_REQUEST_GENDER_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTORS_MESSAGE)
    })
    public ResponseEntity<List<ActorDTO>> getActorsByGender(
            @ApiParam(example = "Male", value = "Gender", allowableValues = "Male, Female", required = true)
            @PathVariable String gender) {
        List<ActorDTO> actorsDTO = this.actorService.getActorsByGender(gender)
                .stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(actorsDTO, HttpStatus.OK);
    }

    @GetMapping(ActorController.NAME + ActorController.SURNAME)
    @ApiOperation(notes = "Retrieve actor that match with name and surname passed by path", value = "Get actor by name and surname")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ActorController.OK_ACTOR_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTOR_MESSAGE)
    })
    public ResponseEntity<ActorDTO> getActorByNameAndSurname(
            @ApiParam(example = "Robert", value = "Name", allowableValues = "Robert, Penélope, Antonio", required = true)
            @PathVariable String name,
            @ApiParam(example = "De Niro", value = "Surname", allowableValues = "De Niro, Cruz, Banderas", required = true)
            @PathVariable String surname) {
        ActorDTO actorDTO = new ActorDTO(this.actorService.getActorByNameAndSurname(name, surname));
        return new ResponseEntity<>(actorDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiOperation(notes = "Create actor with data passed by request body", value = "Create actor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ActorController.CREATED_MESSAGE),
            @ApiResponse(code = 400, message = ActorController.BAD_REQUEST_INCORRECT_ACTOR_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE)
    })
    public ResponseEntity<ActorDTO> createActor(@Valid @RequestBody ActorDTO actorDTO) {
        ActorDTO actorDTOCreated = new ActorDTO(this.actorService.createActor(actorDTO.toActorEntity()));
        return new ResponseEntity<>(actorDTOCreated, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(ActorController.ID)
    @ApiOperation(notes = "Update actor by id passed by path and data passed by request body", value = "Update actor by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ActorController.OK_UPDATED_MESSAGE),
            @ApiResponse(code = 400, message = ActorController.BAD_REQUEST_INCORRECT_ACTOR_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTOR_MESSAGE)
    })
    public ResponseEntity<ActorDTO> updateActor(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody ActorDTO actorDTO) {
        ActorDTO actorDTOUpdated = new ActorDTO(this.actorService.updateActor(id, actorDTO.toActorEntity()));
        return new ResponseEntity<>(actorDTOUpdated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(ActorController.ID)
    @ApiOperation(notes = "Delete actor by id passed by path", value = "Delete actor by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = ActorController.NO_CONTENT_DELETED_MESSAGE),
            @ApiResponse(code = 401, message = ActorController.UNAUTHORIZED_MESSAGE),
            @ApiResponse(code = 404, message = ActorController.NOT_FOUND_ACTOR_MESSAGE)
    })
    public ResponseEntity<HttpStatus> deleteActor(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer id) {
        this.actorService.deleteActor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
