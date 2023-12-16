package com.github.gather.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {

    private Long messageIdx;
    private Long roomId;
    private String content;
    private LocalDateTime sendTime;
    private Long userId;
}