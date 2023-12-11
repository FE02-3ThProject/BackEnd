package com.github.gather.dto.request;


import com.github.gather.entity.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditRequest {

    private Long email;
    private String newNickname;
    private String newEmail;
    private String newPassword;
    private String newPhoneNumber;
    private String newImage;
    private Location newLocation;

    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }


//    private String nickname
//    private String email
//    private String password
//    private String phoneNumber
//    private String image
//    private Location locationId;

}
