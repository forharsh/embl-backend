package com.embl.person.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class PersonDto {

    private Long id;
    @NotEmpty(message = "Please provide first_name")
    private String first_name;
    @NotEmpty(message = "Please provide last_name")
    private String last_name;
    @Positive(message = "Please provide age")
    private int age;
    private String favourite_colour;
    private List<String> hobby;


}
