package com.github.gather.controller;

import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.group.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Tag(name = "GroupAuthority",description = "모임 권한 API")
@RestController
@RequestMapping("/api/group")
public class GroupAuthorityController {

    private final AuthService authService;
    private final GroupService groupService;

    public GroupAuthorityController(AuthService authService, GroupService groupService) {
        this.authService = authService;
        this.groupService = groupService;
    }

    @Operation(summary = "방장 권한 이전" , description = "방장 권한 이전을 진행합니다.")
    @PostMapping("/{groupId}/transferLeader/{newLeaderId}")
    public ResponseEntity<?> transferLeader(@PathVariable Long groupId, @PathVariable Long newLeaderId, HttpServletRequest request) {
        User currentUser = authService.checkToken(request);
        groupService.transferLeader(groupId, newLeaderId, currentUser);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 멤버 추방" , description = "모임 멤버 추방을 진행합니다.")
    @PostMapping("/{groupId}/kick/{userId}")
    public ResponseEntity<?> kickMember(@PathVariable Long groupId, @PathVariable Long userId, HttpServletRequest request) {
        User currentUser = authService.checkToken(request);
        groupService.kickMember(groupId, userId, currentUser);

        return ResponseEntity.ok().build();
    }

}

