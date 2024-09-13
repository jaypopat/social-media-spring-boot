package com.myfirstspringboot.spring_crud.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentContent {
    @NotNull
    private String text;
    @NotNull
    private Long userId;
}
