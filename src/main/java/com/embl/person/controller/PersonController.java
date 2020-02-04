package com.embl.person.controller;

import com.embl.person.dto.PersonDto;
import com.embl.person.entity.Person;
import com.embl.person.exception.PersonNotFoundException;
import com.embl.person.service.PersonService;
import com.embl.person.transformer.Transformer;
import com.embl.person.util.UrlKeys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UrlKeys.BASE)
@Slf4j
@Api(value = "person", tags = "Person API")
public class PersonController {

    private final PersonService personService;
    private final Transformer<Person, PersonDto> transformer;

    @Autowired
    public PersonController(final PersonService personService, final Transformer<Person, PersonDto> transformer) {
        this.personService = personService;
        this.transformer = transformer;
    }

    @GetMapping(UrlKeys.PERSON)
    @ApiOperation(value = "Retrieve all persons")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Authentication is required to access the resource"),
            @ApiResponse(code = 403, message = "You don't have required permission to access the resource"),
            @ApiResponse(code = 404, message = "The resource not found")
    })
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        log.debug("Enter getAllPersons");
        final List<Person> personsList = personService.getAllPersons();
        final List<PersonDto> personsDtoList = personsList.stream()
                .map(transformer::convertToDto)
                .collect(Collectors.toList());
        log.debug("Leaving getAllPersons");
        return new ResponseEntity<>(personsDtoList, HttpStatus.OK);
    }

    @GetMapping(UrlKeys.PERSON_BY_ID)
    @ApiOperation(value = "Retrieve person by personId")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Authentication is required to access the resource"),
            @ApiResponse(code = 403, message = "You don't have required permission to access the resource"),
            @ApiResponse(code = 404, message = "The resource not found")
    })
    public ResponseEntity<PersonDto> getPersonById(@PathVariable("id") final long id) throws PersonNotFoundException {
        log.debug("Enter getPersonById with (id={})", id);
        final Person person = personService.getPersonById(id);
        final PersonDto personDto = transformer.convertToDto(person);
        log.debug("Leaving getPersonById (personDto={})", personDto);
        return new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @PostMapping(UrlKeys.PERSON)
    @ApiOperation(value = "Add person")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Authentication is required to access the resource"),
            @ApiResponse(code = 403, message = "You don't have required permission to access the resource"),
            @ApiResponse(code = 404, message = "The resource not found")
    })
    public ResponseEntity<PersonDto> addPerson(@Valid @RequestBody final PersonDto personDto) {
        log.debug("Enter createPerson (personDto={})", personDto);
        final Person person = transformer.convertToEntity(personDto);
        final PersonDto personDto1 = transformer.convertToDto(personService.addPerson(person));
        log.debug("Leaving createPerson (personDto={})", personDto1);
        return new ResponseEntity<>(personDto1, HttpStatus.CREATED);
    }

    @PutMapping(UrlKeys.PERSON_BY_ID)
    @ApiOperation(value = "Update person by Id with Request body")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 401, message = "Authentication is required to access the resource"),
            @ApiResponse(code = 403, message = "You don't have required permission to access the resource"),
            @ApiResponse(code = 404, message = "The resource not found")
    })
    public ResponseEntity<PersonDto> updatePerson(@PathVariable("id") final long id, @Valid @RequestBody final PersonDto personDto) {
        log.debug("Enter updatePerson (personDto={}, id={})", personDto, id);
        final Person person = transformer.convertToEntity(personDto);
        person.setId(id);
        final PersonDto personDto1 = transformer.convertToDto(personService.updatePerson(person));
        log.debug("Leaving updatePerson (personDto={}, id={})", personDto1, id);
        return new ResponseEntity<>(personDto1, HttpStatus.OK);
    }

    @DeleteMapping(UrlKeys.PERSON_BY_ID)
    @ApiOperation(value = "Delete person by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Authentication is required to access the resource"),
            @ApiResponse(code = 403, message = "You don't have required permission to access the resource")
    })
    public ResponseEntity<PersonDto> deletePerson(@PathVariable("id") final long id) throws PersonNotFoundException {
        log.debug("Enter deletePerson (id={})", id);
        personService.deletePerson(id);
        log.debug("Leaving deletePerson (id={})", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
