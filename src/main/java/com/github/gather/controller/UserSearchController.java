package com.github.gather.controller;


import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserSearchController {

    private final UserSearchService userSearchService;

    @Autowired
    public UserSearchController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String userEmail) {
        UserInfoResponse userInfo = userSearchService.getUserInfo(userEmail);
        return ResponseEntity.ok(userInfo);
    }
}
