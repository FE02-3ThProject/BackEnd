package com.github.gather.dto.request.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원가입 DTO")
public class UserSignupRequest {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "지역 ID")
    private Long locationId;

    @Schema(description = "카테고리 ID")
    private Long categoryId;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "닉네임")
    private String nickname;
}
