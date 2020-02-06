package com.embl.backend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class Person extends BaseEntity {

    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private int age;
    @Column
    private String favourite_colour;
    @Column
    private String hobby;
}
