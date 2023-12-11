package com.github.gather.controller;


import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserSearchController {

    private final UserSearchService userSearchService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String email) {
        UserInfoResponse userInfo = userSearchService.getUserInfo(email);
        return ResponseEntity.ok(userInfo);
    }
}
