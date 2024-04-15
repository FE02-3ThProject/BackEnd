package com.github.gather.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인 DTO")
public class UserLoginRequest {

    private String email;
    private String password;
}
