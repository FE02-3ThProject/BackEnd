package com.github.gather.controller;

import com.github.gather.entity.User;
import com.github.gather.entity.UserGroup;
import com.github.gather.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping("/joined")
    public ResponseEntity<List<UserGroup>> getJoinedUserGroups(@AuthenticationPrincipal User user) {
        List<UserGroup> joinedUserGroups = userGroupService.getJoinedUserGroups(user);
        return ResponseEntity.ok(joinedUserGroups);
    }

    @GetMapping("/bookmarked")
    public ResponseEntity<List<UserGroup>> getBookmarkedUserGroups(@AuthenticationPrincipal User user) {
        List<UserGroup> bookmarkedUserGroups = userGroupService.getBookmarkedUserGroups(user);
        return ResponseEntity.ok(bookmarkedUserGroups);
    }

    @PostMapping("/bookmark/{groupId}")
    public ResponseEntity<String> bookmarkUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupId) {
        userGroupService.bookmarkUserGroup(user, groupId);
        return ResponseEntity.ok("그룹을 즐겨찾기에 추가했습니다.");
    }

    @DeleteMapping("/unbookmark/{groupId}")
    public ResponseEntity<String> unbookmarkUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupId) {
        userGroupService.unbookmarkUserGroup(user, groupId);
        return ResponseEntity.ok("그룹을 즐겨찾기에서 제거했습니다.");
    }

}
