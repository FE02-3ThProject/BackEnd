package com.github.gather.controller;

import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupMembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/group")
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;
    private final AuthService authService;

    public GroupMembershipController(GroupMembershipService groupMembershipService, AuthService authService){
        this.groupMembershipService = groupMembershipService;
        this.authService = authService;
    }

    // 모임 가입
    @PostMapping("/{groupId}/join")
    public ResponseEntity<?> joinGroup(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupMembershipService.joinGroup(groupId, user);
        return ResponseEntity.ok().build();
    }

    // 모임 탈퇴
    @PostMapping("/{groupId}/leave")
    public ResponseEntity<?> leaveGroup(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupMembershipService.leaveGroup(groupId, user);
        return ResponseEntity.ok().build();
    }
}

