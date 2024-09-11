package com.myfirstspringboot.spring_crud.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private UserDTO user;
    private PostDTO post;
}
