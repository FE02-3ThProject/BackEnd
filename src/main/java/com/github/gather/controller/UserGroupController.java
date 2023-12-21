package com.github.gather.controller;

import com.github.gather.entity.*;
import com.github.gather.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Long>> getJoinedUserGroupIds(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 사용자 정보가 없을 때 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = user.getEmail();
        List<Long> joinedGroupIds = userGroupService.getJoinedUserGroupIds(email);

        // 디버깅을 위한 로깅
        System.out.println("가입한 그룹 모임 목록: " + joinedGroupIds);

        return ResponseEntity.ok(joinedGroupIds);
    }

    @GetMapping("/bookmark")
    public ResponseEntity<List<Long>> getBookmarkedUserGroupIds(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 사용자 정보가 없을 때 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = user.getEmail();
        List<Long> bookmarkedGroupIds = userGroupService.getBookmarkedUserGroupIds(email);

        // 디버깅을 위한 로깅
        System.out.println("즐겨찾기한 모임 ID 목록: " + bookmarkedGroupIds);

        return ResponseEntity.ok(bookmarkedGroupIds);
    }


    @DeleteMapping("/unbookmark/{groupId}")
    public ResponseEntity<String> unbookmarkUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupId){
        userGroupService.unbookmarkUserGroup(user, groupId);
        return ResponseEntity.ok("모임을 즐겨찾기에서 제거했습니다.");
    }

}

