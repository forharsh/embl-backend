package com.embl.backend.controller;

import com.embl.backend.dto.PersonDto;
import com.embl.backend.entity.Person;
import com.embl.backend.exception.PersonNotFoundException;
import com.embl.backend.service.PersonService;
import com.embl.backend.transformer.Transformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @Mock
    PersonService personService;

    @Mock
    Transformer<Person, PersonDto> transformer;

    @InjectMocks
    PersonController personController;

    PersonDto personDto;
    Person person;
    List<Person> personList;

    @Before
    public void setUp() {
        personDto = new PersonDto();
        personDto.setAge(33);
        personDto.setFavourite_colour("red");
        personDto.setFirst_name("John");
        personDto.setLast_name("Smith");
        personDto.setHobby(Arrays.asList("shopping"));
        personDto.setId(1L);

        person = new Person();
        person.setAge(33);
        person.setFavourite_colour("red");
        person.setFirst_name("John");
        person.setLast_name("Smith");
        person.setHobby("shopping");

        personList = new ArrayList<>();
        personList.add(person);
    }

    @Test
    public void testShouldKnowHowToGetAllPersonsUsingEndPointWithHttpStatus() {
        when(personService.getAllPersons()).thenReturn(personList);
        when(transformer.convertToDto(any(Person.class))).thenReturn(personDto);
        final ResponseEntity<List<PersonDto>> responseEntity = personController.getAllPersons();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(1);
    }

    @Test
    public void testShouldKnowHowToGetAllPersonByIdUsingEndPointWithHttpStatus() throws PersonNotFoundException {
        when(personService.getPersonById(anyLong())).thenReturn(person);
        when(transformer.convertToDto(any(Person.class))).thenReturn(personDto);
        final ResponseEntity<PersonDto> responseEntity = personController.getPersonById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testShouldKnowHowToAddPersonUsingEndPointWithHttpStatus() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(transformer.convertToEntity(any(PersonDto.class))).thenReturn(person);
        when(personService.addPerson(any(Person.class))).thenReturn(person);
        when(transformer.convertToDto(any(Person.class))).thenReturn(personDto);
        final ResponseEntity<PersonDto> responseEntity = personController.addPerson(personDto);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

}