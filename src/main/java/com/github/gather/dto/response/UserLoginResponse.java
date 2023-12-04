package com.github.gather.dto.response;

import com.github.gather.entity.Location;
import com.github.gather.entity.Role.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponse {
    private String email;
    private String nickname;
    private String phoneNumber;
    private Location location;
    private String image;
    private UserRole userRole;
    private String token;

}
