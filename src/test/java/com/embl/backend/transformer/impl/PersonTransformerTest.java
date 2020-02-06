package com.embl.backend.transformer.impl;

import com.embl.backend.dto.PersonDto;
import com.embl.backend.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonTransformerTest {

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    PersonTransformer transformer;

    @Test
    public void testShowKnowHowToConvertDtoToEntity() {
        when(modelMapper.map(any(PersonDto.class), eq(Person.class))).thenReturn(getPerson());
        final Person person = transformer.convertToEntity(getPersonDto());
        assertEquals(person.getAge(), 33);
    }

    @Test
    public void testShowKnowHowToConvertEntityToDto() {
        when(modelMapper.map(any(Person.class), eq(PersonDto.class))).thenReturn(getPersonDto());
        final PersonDto personDto = transformer.convertToDto(getPerson());
        assertEquals(personDto.getAge(), 33);
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