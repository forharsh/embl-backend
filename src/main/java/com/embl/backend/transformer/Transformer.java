package com.embl.backend.transformer;

public interface Transformer<I,O> {

    I convertToEntity(O input);

    O convertToDto(I input);
}
