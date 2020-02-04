package com.embl.person.transformer.impl;

import com.embl.person.dto.PersonDto;
import com.embl.person.entity.Person;
import com.embl.person.transformer.Transformer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PersonTransformer implements Transformer<Person, PersonDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public PersonTransformer(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Person convertToEntity(final PersonDto personDto) {
        final Person person = modelMapper.map(personDto, Person.class);
        Optional.ofNullable(personDto.getHobby()).ifPresent(hobbies -> person.setHobby(String.join(",", hobbies)));
        person.setId(null);
        return person;
    }

    @Override
    public PersonDto convertToDto(final Person person) {
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        Optional.ofNullable(person.getHobby())
                .ifPresent(hobbies -> personDto.setHobby(Stream.of(hobbies.split(","))
                        .collect(Collectors.toList())
                ));
        return personDto;
    }
}
