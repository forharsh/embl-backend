package com.embl.person.transformer;

public interface Transformer<I,O> {

    I convertToEntity(O input);

    O convertToDto(I input);
}
