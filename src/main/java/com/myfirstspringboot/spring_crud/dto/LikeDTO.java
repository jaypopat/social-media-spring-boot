package com.myfirstspringboot.spring_crud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private PostDTO post;
}
