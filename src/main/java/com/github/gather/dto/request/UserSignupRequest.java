package com.github.gather.dto.request;


import com.github.gather.entity.Location;
import com.github.gather.entity.Role.UserRole;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {

    private String email;
    private Location location;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String image;
    private UserRole userRole;
}
