package com.github.gather.controller;

import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.response.UserEditResponse;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.service.UserEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/edit")
public class UserEditController {

    private final UserEditService userEditService;

    @Autowired
    public UserEditController(UserEditService userEditService) {
        this.userEditService = userEditService;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserEditResponse> editUserInfo(@PathVariable Long userId, @RequestBody UserEditRequest userEditRequest){
        try {
            UserEditResponse response = userEditService.editUserInfo(userId, userEditRequest);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

