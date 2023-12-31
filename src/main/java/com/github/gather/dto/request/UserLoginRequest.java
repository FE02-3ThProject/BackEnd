package com.github.gather.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;
}
