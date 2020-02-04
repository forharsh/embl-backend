package com.embl.person.dto;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class UserDtoTest {

    @Test
    public void shouldKnowHowToConvertDtoToString() {
        final UserDto userDto = new UserDto();
        final UserDto anotherUserDto = new UserDto();

        final String expectedResponse = "UserDto(username=null)";
        assertEquals(expectedResponse, userDto.toString());
        assertEquals(anotherUserDto, userDto);
        assertEquals(anotherUserDto.hashCode(), userDto.hashCode());
    }

    @Test
    public void shouldKnowHowTwoDtosDoNotHaveSameStringLiteralWhenDeclaredDifferently() {
        final UserDto userDto = new UserDto();
        final UserDto anotherUserDto = new UserDto();

        userDto.setUsername("username-1");
        userDto.setPassword("password");

        assertNotEquals(anotherUserDto.toString(), userDto.toString());
        assertNotEquals(anotherUserDto, userDto);
        assertNotEquals(anotherUserDto.hashCode(), userDto.hashCode());
    }

    @Test
    public void shouldAppropriatelyTestTheGetterAndSetters() {
        final UserDto userDto = new UserDto();
        userDto.setUsername("username-1");
        userDto.setPassword("password");

        assertEquals("username-1", userDto.getUsername());
        assertEquals("password", userDto.getPassword());
    }
}

