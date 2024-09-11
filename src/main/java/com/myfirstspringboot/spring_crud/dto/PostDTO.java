package com.myfirstspringboot.spring_crud.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO user;
    private List<LikeDTO> likes;
}
