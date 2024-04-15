package com.github.gather.controller;

import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupMembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "GroupMembership",description = "모임가입 및 탈퇴 API")
@RestController
@RequestMapping("/api/group")
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;
    private final AuthService authService;

    public GroupMembershipController(GroupMembershipService groupMembershipService, AuthService authService){
        this.groupMembershipService = groupMembershipService;
        this.authService = authService;
    }

    @Operation(summary = "모임 가입" , description = "모임 가입을 진행합니다.")
    @PostMapping("/{groupId}/join")
    public ResponseEntity<?> joinGroup(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupMembershipService.joinGroup(groupId, user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 탈퇴" , description = "모임 탈퇴를 진행합니다.")
    @PostMapping("/{groupId}/leave")
    public ResponseEntity<?> leaveGroup(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupMembershipService.leaveGroup(groupId, user);
        return ResponseEntity.ok().build();
    }
}

