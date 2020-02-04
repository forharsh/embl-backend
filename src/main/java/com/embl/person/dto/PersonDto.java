package com.embl.person.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@ApiModel
public class PersonDto {

    private Long id;

    @ApiModelProperty(notes = "Provided person first name", required = true, example = "John")
    @NotEmpty(message = "Please provide first_name")
    private String first_name;

    @NotEmpty(message = "Please provide last_name")
    @ApiModelProperty(notes = "Provided person second name", required = true, example = "Smith")
    private String last_name;

    @Positive(message = "Please provide age")
    @ApiModelProperty(notes = "Provided person age", required = true, example = "33")
    private int age;

    @ApiModelProperty(notes = "Provided person favourite color", example = "red")
    private String favourite_colour;

    @ApiModelProperty(notes = "Provided person hobbies", name = "hobby")
    private List<String> hobby;


}
