package com.myfirstspringboot.spring_crud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostContent {

    @NotNull
    private String content;
    @NotNull
    private Long userId;
}
