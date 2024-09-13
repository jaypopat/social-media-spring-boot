package com.myfirstspringboot.spring_crud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserContent {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
