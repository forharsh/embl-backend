package com.embl.person.dto;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class AuthTokenTest {

    @Test
    public void shouldKnowHowToConvertDtoToString() {
        final AuthToken authToken = new AuthToken("", "");
        final AuthToken anotherAuthToken = new AuthToken("", "");

        final String expectedResponse = "AuthToken(username=, token=)";
        assertEquals(expectedResponse, authToken.toString());
        assertEquals(anotherAuthToken, authToken);
        assertEquals(anotherAuthToken.hashCode(), authToken.hashCode());
    }

    @Test
    public void shouldKnowHowTwoDtosDoNotHaveSameStringLiteralWhenDeclaredDifferently() {
        final AuthToken authToken = new AuthToken("username-1", "token-1");
        final AuthToken anotherAuthToken = new AuthToken("username-2", "token-2");


        assertNotEquals(anotherAuthToken.toString(), authToken.toString());
        assertNotEquals(anotherAuthToken, authToken);
        assertNotEquals(anotherAuthToken.hashCode(), authToken.hashCode());
    }

    @Test
    public void shouldAppropriatelyTestTheGetterAndSetters() {
        final AuthToken authToken = new AuthToken("username-1", "token-1");

        assertEquals("username-1", authToken.getUsername());
        assertEquals("token-1", authToken.getToken());
    }

}