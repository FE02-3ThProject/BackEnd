package com.github.gather.dto.request.user;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {

    private String email;
    private Long locationId;
    private Long categoryId;
    private String password;
    private String nickname;
}
