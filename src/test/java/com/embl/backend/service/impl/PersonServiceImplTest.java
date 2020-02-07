package com.embl.backend.service.impl;


import com.embl.backend.entity.Person;
import com.embl.backend.exception.PersonNotFoundException;
import com.embl.backend.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    @Test
    public void testShouldKnowHowToAddPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(getPerson());
        personService.addPerson(getPerson());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test(expected = PersonNotFoundException.class)
    public void testShouldKnowHowToUpdatePerson_WhenPersonNotExist() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
        personService.updatePerson(getPerson());
        verify(personRepository, times(0)).save(any(Person.class));
    }

    @Test
    public void testShouldKnowHowToUpdatePerson() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(new Person()));
        when(personRepository.save(any(Person.class))).thenReturn(getPerson());
        personService.updatePerson(getPerson());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void testShouldKnowHowToDeletePersonWhenPersonExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(new Person()));
        personService.deletePerson(3L);
        verify(personRepository, times(1)).deleteById(any(Long.class));
    }

    @Test(expected = PersonNotFoundException.class)
    public void testShouldKnowHowToDeletePersonWhenPersonDoesNotExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
        personService.deletePerson(3L);
        verify(personRepository, times(1)).delete(any(Person.class));
    }

    @Test
    public void testShouldKnowHowToGetAllPersons() {
        when(personRepository.findAll()).thenReturn(getPersonList());
        personService.getAllPersons();
        verify(personRepository, times(1)).findAll();
    }

    @Test(expected = PersonNotFoundException.class)
    public void testShouldKnowHowToGetPersonByIdWhenPersonDoesNotExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
        personService.getPersonById(1L);
        verify(personRepository, times(0)).getOne(any(Long.class));
    }

    @Test
    public void testShouldKnowHowToGetPersonByIdWhenPersonExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(new Person()));
        when(personRepository.getOne(any(Long.class))).thenReturn(getPerson());
        personService.getPersonById(1L);
        verify(personRepository, times(1)).getOne(any(Long.class));
    }

    private List<Person> getPersonList() {
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
        smith.setId(1L);
        return smith;
    }


}