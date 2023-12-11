package com.github.gather.dto.response;

import com.github.gather.entity.Location;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String image;
    private Location locationId;


}
