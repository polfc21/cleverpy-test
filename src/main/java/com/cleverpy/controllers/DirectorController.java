package com.cleverpy.controllers;

import com.cleverpy.dtos.DirectorDTO;
import com.cleverpy.services.DirectorService;
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
@RequestMapping(DirectorController.DIRECTORS)
public class DirectorController {

    public static final String DIRECTORS = "/directors";
    public static final String AGE = "/age/{age}";
    public static final String COUNTRY = "/country/{country}";
    public static final String GENDER = "/gender/{gender}";
    public static final String NAME = "/name/{name}";
    public static final String SURNAME = "/surname/{surname}";
    public static final String ID = "/{id}";

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        List<DirectorDTO> directorsDTO = this.directorService.getAllDirectors();
        if (directorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.AGE)
    public ResponseEntity<List<DirectorDTO>> getDirectorsByAge(@PathVariable @Positive Integer age) {
        List<DirectorDTO> directorsDTO = this.directorService.getDirectorsByAge(age);
        if (directorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.COUNTRY)
    public ResponseEntity<List<DirectorDTO>> getDirectorsByCountry(@PathVariable @NotBlank String country) {
        List<DirectorDTO> directorsDTO = this.directorService.getDirectorsByCountry(country);
        if (directorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.GENDER)
    public ResponseEntity<List<DirectorDTO>> getDirectorsByGender(@PathVariable @Gender String gender) {
        List<DirectorDTO> directorsDTO = this.directorService.getDirectorsByGender(gender);
        if (directorsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.NAME + DirectorController.SURNAME)
    public ResponseEntity<DirectorDTO> getDirectorsByNameAndSurname(@PathVariable @NotBlank String name,
                                                                          @PathVariable @NotBlank String surname) {
        DirectorDTO directorDTO = this.directorService.getDirectorByNameAndSurname(name, surname);
        if (directorDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(directorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO directorDTOCreated = this.directorService.createDirector(directorDTO);
        return new ResponseEntity<>(directorDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping(DirectorController.ID)
    public ResponseEntity<DirectorDTO> updateDirector(@PathVariable Integer id, @Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO directorDTOUpdated = this.directorService.updateDirector(id, directorDTO);
        if (directorDTOUpdated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(directorDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping(DirectorController.ID)
    public ResponseEntity<HttpStatus> deleteDirector(@PathVariable Integer id) {
        this.directorService.deleteDirector(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
