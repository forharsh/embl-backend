package com.embl.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@ApiModel
public class UserDto {

    @ApiModelProperty(notes = "Provided user name", required =true, example = "admin")
    private String username;

    @ApiModelProperty(notes = "Base 64 encoded password", required =true, example = "YWRtaW4=")
    private String password;
}
