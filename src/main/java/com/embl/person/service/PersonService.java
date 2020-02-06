package com.embl.person.service;

import com.embl.person.entity.Person;
import com.embl.person.exception.PersonNotFoundException;

import java.util.List;

/**
 * Perform operations on person entity.
 */
public interface PersonService {

    /**
     * Used to get all persons.
     * @return
     */
    List<Person> getAllPersons();

    /**
     * To create a person entity
     * @param person
     * @return
     */
    Person addPerson(Person person);

    /**
     * To delete a person entity.
     * @param id
     * @throws PersonNotFoundException
     */
    void deletePerson(Long id) throws PersonNotFoundException;

    /**
     * To Update person entity.
     * @param person
     * @return
     */
    Person updatePerson(Person person);

    /**
     * Retrieve person by Person Id.
     * @param id
     * @return
     * @throws PersonNotFoundException
     */
    Person getPersonById(Long id) throws PersonNotFoundException;
}
