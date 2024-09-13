package com.myfirstspringboot.spring_crud.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentDTO {
    private Long id;
    private String text;
    private UserDTO user;
    private PostDTO post;
}
