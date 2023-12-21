package com.github.gather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupPostDto {

    private Long postId;
    private Long groupId;
    private String email;
    private String title;
    private String content;
    private LocalDate postedAt;
}

