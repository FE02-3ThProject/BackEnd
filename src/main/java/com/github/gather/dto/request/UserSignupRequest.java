package com.github.gather.dto.request;


import com.github.gather.entity.Category;
import com.github.gather.entity.Location;
import com.github.gather.entity.Role.UserRole;
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
    private String image;
}
