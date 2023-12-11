package com.github.gather.controller;

import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.response.UserEditResponse;
import com.github.gather.service.UserEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserEditController {

    private final UserEditService userEditService;

    @Autowired
    public UserEditController(UserEditService userEditService) {
        this.userEditService = userEditService;
    }

    @PostMapping("/edit")
    public ResponseEntity<UserEditResponse> editUser(@RequestBody UserEditRequest userEditRequest) {
        UserEditResponse userEditResponse = userEditService.editUser(userEditRequest);
        return ResponseEntity.ok(userEditResponse);
    }
}
