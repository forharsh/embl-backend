package com.embl.backend.service;

import com.embl.backend.entity.Person;
import com.embl.backend.exception.PersonNotFoundException;

import java.util.List;

/**
 * Perform operations on person entity.
 */
public interface PersonService {

    /**
     * Used to get all persons.
     * @return all persons.
     */
    List<Person> getAllPersons();

    /**
     * To create a person entity
     * @param  person to create
     * @return created person
     */
    Person addPerson(Person person);

    /**
     * To delete a person entity.
     * @param id of person to delete
     * @throws PersonNotFoundException if person not exists.
     */
    void deletePerson(Long id) throws PersonNotFoundException;

    /**
     * To Update person entity.
     * @param person to update
     * @return updated person
     */
    Person updatePerson(Person person);

    /**
     * Retrieve person by Person Id.
     * @param id of person
     * @return person
     * @throws PersonNotFoundException if person not exists.
     */
    Person getPersonById(Long id) throws PersonNotFoundException;
}
