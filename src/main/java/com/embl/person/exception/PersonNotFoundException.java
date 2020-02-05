package com.embl.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PersonNotFoundException(final String message) {
        super(message);
    }

}
