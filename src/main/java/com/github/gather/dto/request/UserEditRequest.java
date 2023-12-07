package com.github.gather.dto.request;

import com.github.gather.entity.Location;
import com.github.gather.entity.Role.UserRole;

public class UserEditRequest {
    private String email;
    private Location location;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String image;
    private UserRole userRole;

    public String getEditemail() {
        return editemail;
    }

    public void setEditemail(String editemail) {
        this.editemail = editemail;
    }

    public Location getEditlocation() {
        return editlocation;
    }

    public void setEditlocation(Location editlocation) {
        this.editlocation = editlocation;
    }

    public String getEditpassword() {
        return editpassword;
    }

    public void setEditpassword(String editpassword) {
        this.editpassword = editpassword;
    }

    public String getEditnickname() {
        return editnickname;
    }

    public void setEditnickname(String editnickname) {
        this.editnickname = editnickname;
    }

    public String getEditphoneNumber() {
        return editphoneNumber;
    }

    public void setEditphoneNumber(String editphoneNumber) {
        this.editphoneNumber = editphoneNumber;
    }

    public String getEditimage() {
        return editimage;
    }

    public void setEditimage(String editimage) {
        this.editimage = editimage;
    }

    public UserRole getEdituserRole() {
        return edituserRole;
    }

    public void setEdituserRole(UserRole edituserRole) {
        this.edituserRole = edituserRole;
    }

    private String editemail;
    private Location editlocation;
    private String editpassword;
    private String editnickname;
    private String editphoneNumber;
    private String editimage;
    private UserRole edituserRole;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
