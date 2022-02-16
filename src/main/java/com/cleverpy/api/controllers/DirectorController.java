package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.DirectorDTO;
import com.cleverpy.domain.services.DirectorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(DirectorController.DIRECTORS)
@Api(tags = "API Rest. Directors management.")
public class DirectorController {

    public static final String DIRECTORS = "/directors";
    public static final String AGE = "/age/{age}";
    public static final String COUNTRY = "/country/{country}";
    public static final String GENDER = "/gender/{gender}";
    public static final String NAME = "/name/{name}";
    public static final String SURNAME = "/surname/{surname}";
    public static final String ID = "/{id}";

    public static final String OK_DIRECTOR_MESSAGE = "Response ok if the director was found";
    public static final String OK_DIRECTORS_MESSAGE = "Response ok if the directors were found";
    public static final String OK_UPDATED_MESSAGE = "Response ok if the director was updated";
    public static final String CREATED_MESSAGE = "Response created if the director was created";
    public static final String NO_CONTENT_DELETED_MESSAGE = "Response no content if the director was deleted";
    public static final String NOT_FOUND_DIRECTOR_MESSAGE = "Response not found if the director doesn't exists";
    public static final String NOT_FOUND_DIRECTORS_MESSAGE = "Response not found if there aren't directors";
    public static final String BAD_REQUEST_GENDER_MESSAGE = "Response bad request if the gender is incorrect";
    public static final String BAD_REQUEST_INCORRECT_DIRECTOR_MESSAGE = "Response bad request if the director provided is not correct";

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    @ApiOperation(notes = "Retrieve all directors that are saved in the database", value = "Get all directors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DirectorController.OK_DIRECTORS_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTORS_MESSAGE)
    })
    public ResponseEntity<List<DirectorDTO>> getAllDirectors(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "3") int size) {
        List<DirectorDTO> directorsDTO = this.directorService.getAllDirectors(PageRequest.of(page, size))
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.AGE)
    @ApiOperation(notes = "Retrieve all directors that match with age passed by path", value = "Get directors by age")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DirectorController.OK_DIRECTORS_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTORS_MESSAGE)
    })
    public ResponseEntity<List<DirectorDTO>> getDirectorsByAge(
            @ApiParam(example = "25", value = "Age", allowableValues = "25, 43, 67, 76, 39", required = true)
            @PathVariable Integer age) {
        List<DirectorDTO> directorsDTO = this.directorService.getDirectorsByAge(age)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.COUNTRY)
    @ApiOperation(notes = "Retrieve all directors that match with country passed by path", value = "Get directors by country")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DirectorController.OK_DIRECTORS_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTORS_MESSAGE)
    })
    public ResponseEntity<List<DirectorDTO>> getDirectorsByCountry(
            @ApiParam(example = "Spain", value = "Country", allowableValues = "Spain, United States, Italy", required = true)
            @PathVariable String country) {
        List<DirectorDTO> directorsDTO = this.directorService.getDirectorsByCountry(country)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.GENDER)
    @ApiOperation(notes = "Retrieve all directors that match with gender passed by path", value = "Get directors by gender")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DirectorController.OK_DIRECTORS_MESSAGE),
            @ApiResponse(code = 400, message = DirectorController.BAD_REQUEST_GENDER_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTORS_MESSAGE)
    })
    public ResponseEntity<List<DirectorDTO>> getDirectorsByGender(
            @ApiParam(example = "Male", value = "Gender", allowableValues = "Male, Female", required = true)
            @PathVariable String gender) {
        List<DirectorDTO> directorsDTO = this.directorService.getDirectorsByGender(gender)
                .stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(directorsDTO, HttpStatus.OK);
    }

    @GetMapping(DirectorController.NAME + DirectorController.SURNAME)
    @ApiOperation(notes = "Retrieve director that match with name and surname passed by path", value = "Get director by name and surname")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DirectorController.OK_DIRECTOR_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTOR_MESSAGE)
    })
    public ResponseEntity<DirectorDTO> getDirectorByNameAndSurname(
            @ApiParam(example = "Martin", value = "Name", allowableValues = "Martin, Pedro, Quentin", required = true)
            @PathVariable String name,
            @ApiParam(example = "Scorsese", value = "Surname", allowableValues = "Scorsese, Almodóvar, Tarantino", required = true)
            @PathVariable String surname) {
        DirectorDTO directorDTO = new DirectorDTO(this.directorService.getDirectorByNameAndSurname(name, surname));
        return new ResponseEntity<>(directorDTO, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(notes = "Create director with data passed by request body", value = "Create director")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = DirectorController.CREATED_MESSAGE),
            @ApiResponse(code = 400, message = DirectorController.BAD_REQUEST_INCORRECT_DIRECTOR_MESSAGE),
    })
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO directorDTOCreated = new DirectorDTO(this.directorService.createDirector(directorDTO.toDirectorEntity()));
        return new ResponseEntity<>(directorDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping(DirectorController.ID)
    @ApiOperation(notes = "Update director by id passed by path and data passed by request body", value = "Update director by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DirectorController.OK_UPDATED_MESSAGE),
            @ApiResponse(code = 400, message = DirectorController.BAD_REQUEST_INCORRECT_DIRECTOR_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTOR_MESSAGE)
    })
    public ResponseEntity<DirectorDTO> updateDirector(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO directorDTOUpdated = new DirectorDTO(this.directorService.updateDirector(id, directorDTO.toDirectorEntity()));
        return new ResponseEntity<>(directorDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping(DirectorController.ID)
    @ApiOperation(notes = "Delete director by id passed by path", value = "Delete director by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = DirectorController.NO_CONTENT_DELETED_MESSAGE),
            @ApiResponse(code = 404, message = DirectorController.NOT_FOUND_DIRECTOR_MESSAGE)
    })
    public ResponseEntity<HttpStatus> deleteDirector(
            @ApiParam(example = "1", value = "Id", allowableValues = "1, 2, 3, 4, 5", required = true)
            @PathVariable Integer id) {
        this.directorService.deleteDirector(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
