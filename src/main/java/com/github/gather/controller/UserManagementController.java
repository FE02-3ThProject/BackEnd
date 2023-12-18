package com.github.gather.controller;


import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.response.UserEditResponse;
import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.service.IntroductionService;
import com.github.gather.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final IntroductionService introductionService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String email) {
        try {
            UserInfoResponse userInfo = userManagementService.getUserInfo(email);
            return ResponseEntity.ok(userInfo);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserEditResponse> editUserInfo(@PathVariable Long userId, @RequestBody UserEditRequest userEditRequest) {
        try {
            UserEditResponse response = userManagementService.editUserInfo(userId, userEditRequest);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
