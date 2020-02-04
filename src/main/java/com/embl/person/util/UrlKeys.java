package com.embl.person.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlKeys {

    public static final String BASE = "/api/v1";
    public static final String PERSON = "/person";
    public static final String PERSON_BY_ID = PERSON + "/{id}";
    public static final String TOKEN_GENERATE = BASE + "/generate-token";
    public static final String H2_CONSOLE = "/h2-console/**";
}
