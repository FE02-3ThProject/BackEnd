package com.github.gather.dto.response;

import com.github.gather.entity.Location;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class UserEditResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String image;
    private Location location;

    public void setPassword(String password) {
        this.password = password;
    }
}
