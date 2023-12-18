package com.github.gather.controller;


import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.response.ErrorResponse;
import com.github.gather.dto.response.UserEditResponse;
import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(@RequestParam String email) throws UserNotFoundException {
        UserInfoResponse userInfo = userManagementService.getUserInfo(email);
        // 예외가 발생하지 않으면 정상적인 응답을 반환
        return userInfo;
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
