package com.embl.person.dto;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonDtoTest {

    @Test
    public void shouldKnowHowToConvertDtoToString() {
        final PersonDto personDto = new PersonDto();
        final PersonDto anotherPersonDto = new PersonDto();

        final String expectedResponse = "PersonDto(id=null, first_name=null, last_name=null, age=0, favourite_colour=null, hobby=null)";
        assertEquals(expectedResponse, personDto.toString());
        assertEquals(anotherPersonDto, personDto);
        assertEquals(anotherPersonDto.hashCode(), personDto.hashCode());
    }

    @Test
    public void shouldKnowHowTwoDtosDoNotHaveSameStringLiteralWhenDeclaredDifferently() {
        final PersonDto personDto = new PersonDto();
        final PersonDto anotherPersonDto = new PersonDto();

        personDto.setAge(33);
        personDto.setId(1l);
        personDto.setFavourite_colour("red");
        personDto.setFirst_name("John");
        personDto.setLast_name("Smith");
        personDto.setHobby(Arrays.asList("shopping"));

        assertNotEquals(anotherPersonDto.toString(), personDto.toString());
        assertNotEquals(anotherPersonDto, personDto);
        assertNotEquals(anotherPersonDto.hashCode(), personDto.hashCode());
    }

    @Test
    public void shouldAppropriatelyTestTheGetterAndSetters() {
        final PersonDto personDto = new PersonDto();

        personDto.setAge(33);
        personDto.setFavourite_colour("red");
        personDto.setFirst_name("John");
        personDto.setLast_name("Smith");
        personDto.setHobby(Arrays.asList("shopping"));
        personDto.setId(1l);

        assertEquals(33, personDto.getAge());
        assertEquals("red", personDto.getFavourite_colour());
        assertEquals("John", personDto.getFirst_name());
        assertEquals("Smith", personDto.getLast_name());
        assertEquals(Long.valueOf(1), personDto.getId());
    }


}