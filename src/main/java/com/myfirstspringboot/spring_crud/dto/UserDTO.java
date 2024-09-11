package com.myfirstspringboot.spring_crud.dto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String email;
}
