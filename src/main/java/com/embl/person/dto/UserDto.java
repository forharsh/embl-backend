package com.embl.person.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class UserDto {
    private String username;
    private String password;
}
