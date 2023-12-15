package com.github.gather.controller;

import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.UserService;
import com.github.gather.service.group.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/group")
public class GroupAuthorityController {

    private final AuthService authService;
    private final GroupService groupService;
    private final UserService userService;

    public GroupAuthorityController(AuthService authService, GroupService groupService, UserService userService) {
        this.authService = authService;
        this.groupService = groupService;
        this.userService = userService;
    }

    // 방장 권한 이전
    @PostMapping("/{groupId}/transferLeader/{newLeaderId}")
    public ResponseEntity<?> transferLeader(@PathVariable Long groupId, @PathVariable Long newLeaderId, HttpServletRequest request) {
        User currentUser = authService.checkToken(request);
        groupService.transferLeader(groupId, newLeaderId, currentUser);

        return ResponseEntity.ok().build();
    }
}

