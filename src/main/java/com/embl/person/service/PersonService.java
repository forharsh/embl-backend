package com.embl.person.service;

import com.embl.person.entity.Person;
import com.embl.person.exception.PersonNotFoundException;

import java.util.List;

public interface PersonService {

    List<Person> getAllPersons();

    Person addPerson(Person person);

    void deletePerson(Long id) throws PersonNotFoundException;

    Person updatePerson(Person person);

    Person getPersonById(Long id) throws PersonNotFoundException;
}
