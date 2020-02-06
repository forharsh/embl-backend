package com.embl.backend.util;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class UrlKeysTest {

    @Test
    public void testUrlKeys() {
        assertEquals(UrlKeys.PERSON, "/person");
        assertEquals(UrlKeys.BASE, "/api/v1");
        assertEquals(UrlKeys.PERSON_BY_ID, "/person/{id}");
        assertEquals(UrlKeys.TOKEN_GENERATE, "/api/v1/generate-token");
        assertEquals(UrlKeys.H2_CONSOLE, "/h2-console/**");
    }

}