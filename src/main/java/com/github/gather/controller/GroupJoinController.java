package com.github.gather.controller;

import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupJoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/group")
public class GroupJoinController {

    private final GroupJoinService groupJoinService;
    private final AuthService authService;

    public GroupJoinController(GroupJoinService groupJoinService, AuthService authService){
        this.groupJoinService = groupJoinService;
        this.authService = authService;
    }

    // 모임 가입
    @PostMapping("/{groupId}/join")
    public ResponseEntity<?> joinGroup(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupJoinService.joinGroup(groupId, user);
        return ResponseEntity.ok().build();
    }
}

