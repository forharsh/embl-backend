package com.embl.backend.service.impl;

import com.embl.backend.entity.Person;
import com.embl.backend.exception.PersonNotFoundException;
import com.embl.backend.repository.PersonRepository;
import com.embl.backend.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void deletePerson(final Long id) throws PersonNotFoundException {
        checkPersonExists(id);
        personRepository.deleteById(id);
    }

    @Override
    public Person addPerson(final Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(final Long id) throws PersonNotFoundException {
        checkPersonExists(id);
        return personRepository.getOne(id);
    }

    private void checkPersonExists(final Long id) throws PersonNotFoundException {
        final Optional<Person> personDb = personRepository.findById(id);
        personDb.orElseThrow(() -> {
            LOGGER.debug("Person Not Found with (id={})", id);
            return new PersonNotFoundException("Person Not Found with id = " + id);
        });
    }

    @Override
    public Person updatePerson(final Person person) {
        return personRepository.save(person);
    }
}
