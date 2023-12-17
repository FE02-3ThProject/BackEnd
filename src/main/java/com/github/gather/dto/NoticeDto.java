package com.github.gather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {

    private Long noticeIdx;
    private String title;
    private String content;
    private Long userId;
    private LocalDate createAt;
    private String email;
}
