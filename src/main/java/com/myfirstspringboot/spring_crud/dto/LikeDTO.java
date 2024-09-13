package com.myfirstspringboot.spring_crud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private PostDTO post;
}
