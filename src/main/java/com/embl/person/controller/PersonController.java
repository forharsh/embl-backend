package com.embl.person.controller;

import com.embl.person.dto.PersonDto;
import com.embl.person.entity.Person;
import com.embl.person.exception.PersonNotFoundException;
import com.embl.person.service.PersonService;
import com.embl.person.transformer.Transformer;
import com.embl.person.util.UrlKeys;
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
public class PersonController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PersonService personService;
    private final Transformer<Person, PersonDto> transformer;

    @Autowired
    public PersonController(final PersonService personService, final Transformer<Person, PersonDto> transformer) {
        this.personService = personService;
        this.transformer = transformer;
    }

    @GetMapping(UrlKeys.PERSON)
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        LOGGER.debug("Enter getAllPersons");
        final List<Person> personsList = personService.getAllPersons();
        final List<PersonDto> personsDtoList = personsList.stream()
                .map(transformer::convertToDto)
                .collect(Collectors.toList());
        LOGGER.debug("Leaving getAllPersons");
        return new ResponseEntity<>(personsDtoList, HttpStatus.OK);
    }

    @GetMapping(UrlKeys.PERSON_BY_ID)
    public ResponseEntity<PersonDto> getPersonById(@PathVariable("id") final long id) throws PersonNotFoundException {
        LOGGER.debug("Enter getPersonById with (id={})", id);
        final Person person = personService.getPersonById(id);
        final PersonDto personDto = transformer.convertToDto(person);
        LOGGER.debug("Leaving getPersonById (personDto={})", personDto);
        return new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @PostMapping(UrlKeys.PERSON)
    public ResponseEntity<PersonDto> addPerson(@Valid @RequestBody final PersonDto personDto) {
        LOGGER.debug("Enter createPerson (personDto={})", personDto);
        final Person person = transformer.convertToEntity(personDto);
        final PersonDto personDto1 = transformer.convertToDto(personService.addPerson(person));
        LOGGER.debug("Leaving createPerson (personDto={})", personDto1);
        return new ResponseEntity<>(personDto1, HttpStatus.CREATED);
    }

    @PutMapping(UrlKeys.PERSON_BY_ID)
    public ResponseEntity<PersonDto> updatePerson(@PathVariable("id") final Long id, @Valid @RequestBody final PersonDto personDto) {
        LOGGER.debug("Enter updatePerson (personDto={}, id={})", personDto, id);
        final Person person = transformer.convertToEntity(personDto);
        person.setId(id);
        final PersonDto personDto1 = transformer.convertToDto(personService.updatePerson(person));
        LOGGER.debug("Leaving updatePerson (personDto={}, id={})", personDto1, id);
        return new ResponseEntity<>(personDto1, HttpStatus.OK);
    }

    @DeleteMapping(UrlKeys.PERSON_BY_ID)
    public ResponseEntity<PersonDto> deletePerson(@PathVariable("id") final long id) throws PersonNotFoundException {
        LOGGER.debug("Enter deletePerson (id={})", id);
        personService.deletePerson(id);
        LOGGER.debug("Leaving deletePerson (id={})", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
