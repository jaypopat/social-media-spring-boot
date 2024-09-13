package com.myfirstspringboot.spring_crud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserDTO {
    private long id;
    private String name;
    private String email;
}
