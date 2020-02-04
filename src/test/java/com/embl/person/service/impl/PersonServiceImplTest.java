package com.embl.person.service.impl;


import com.embl.person.entity.Person;
import com.embl.person.exception.PersonNotFoundException;
import com.embl.person.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personService;

    @Test
    public void testShouldKnowHowToUpdatePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(getPerson());
        final Person person = personService.updatePerson(getPerson());
        assertEquals("John", person.getFirst_name());
        assertEquals("Smith", person.getLast_name());
    }

    @Test
    public void testShouldKnowHowToDeletePersonWhenPersonExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(new Person()));
        personService.deletePerson(3l);
        verify(personRepository, times(1)).deleteById(any(Long.class));
    }

    @Test(expected = PersonNotFoundException.class)
    public void testShouldKnowHowToDeletePersonWhenPersonDoesNotExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
        personService.deletePerson(3l);
    }

    @Test
    public void testShouldKnowHowToGetAllPersons() {
        when(personRepository.findAll()).thenReturn(getPersonList());
        final List<Person> allPersons = personService.getAllPersons();
        assertEquals(1, allPersons.size());
    }

    @Test(expected = PersonNotFoundException.class)
    public void testShouldKnowHowToGetPersonByIdWhenPersonDoesNotExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));
        personService.getPersonById(1l);
        verify(personRepository, times(0)).getOne(any(Long.class));
    }

    @Test
    public void testShouldKnowHowToGetPersonByIdWhenPersonExists() throws PersonNotFoundException {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(new Person()));
        when(personRepository.getOne(any(Long.class))).thenReturn(getPerson());
        final Person personById = personService.getPersonById(1l);
        verify(personRepository, times(1)).getOne(any(Long.class));
        assertEquals("Smith", personById.getLast_name());
    }

    @Test
    public void testShouldKnowHowToUpdatePersonDtl() {
        when(personRepository.save(any(Person.class))).thenReturn(getPerson());
        personService.updatePerson(getPerson());
        verify(personRepository, times(1)).save(any(Person.class));
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
        return smith;
    }


}