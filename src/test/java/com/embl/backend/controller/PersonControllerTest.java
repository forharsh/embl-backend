package com.embl.backend.controller;

import com.embl.backend.transformer.Transformer;
import com.embl.backend.dto.PersonDto;
import com.embl.backend.entity.Person;
import com.embl.backend.exception.PersonNotFoundException;
import com.embl.backend.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @Mock
    PersonService personService;

    @Mock
    Transformer<Person, PersonDto> transformer;

    @InjectMocks
    PersonController personController;

    @Test
    public void testShouldKnowHowToGetAllPersonsUsingEndPointWithHttpStatus() {
        Mockito.when(personService.getAllPersons()).thenReturn(getAllPersons());
        Mockito.when(transformer.convertToDto(any(Person.class))).thenReturn(getPersonDto());
        final ResponseEntity<List<PersonDto>> responseEntity = personController.getAllPersons();
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertEquals(responseEntity.getBody().size(), 1);
    }

    @Test
    public void testShouldKnowHowToGetAllPersonByIdUsingEndPointWithHttpStatus() throws PersonNotFoundException {
        Mockito.when(personService.getPersonById(anyLong())).thenReturn(getPerson());
        Mockito.when(transformer.convertToDto(any(Person.class))).thenReturn(getPersonDto());
        final ResponseEntity<PersonDto> responseEntity = personController.getPersonById(1L);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void testShouldKnowHowToAddPersonUsingEndPointWithHttpStatus() {
        Mockito.when(transformer.convertToEntity(any(PersonDto.class))).thenReturn(getPerson());
        Mockito.when(personService.addPerson(any(Person.class))).thenReturn(getPerson());
        Mockito.when(transformer.convertToDto(any(Person.class))).thenReturn(getPersonDto());
        final ResponseEntity<PersonDto> responseEntity = personController.addPerson(getPersonDto());
        assertEquals(responseEntity.getStatusCodeValue(), 201);
    }

    private PersonDto getPersonDto() {
        final PersonDto personDto = new PersonDto();
        personDto.setAge(33);
        personDto.setFavourite_colour("red");
        personDto.setFirst_name("John");
        personDto.setLast_name("Smith");
        personDto.setHobby(Arrays.asList("shopping"));
        return personDto;
    }

    private List<Person> getAllPersons() {
        final Person smith = new Person();
        smith.setAge(33);
        smith.setFavourite_colour("red");
        smith.setFirst_name("John");
        smith.setLast_name("Smith");
        smith.setHobby("shopping");
        return Arrays.asList(smith);
    }

    private Person getPerson() {
        final Person smith = new Person();
        smith.setAge(33);
        smith.setFavourite_colour("red");
        smith.setFirst_name("John");
        smith.setLast_name("Smith");
        smith.setHobby("shopping");
        return smith;
    }

}