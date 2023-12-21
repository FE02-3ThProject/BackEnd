package com.github.gather.controller;

import com.github.gather.dto.response.BookMarkResponse;
import com.github.gather.entity.*;
import com.github.gather.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;



    @GetMapping("/joined")
    public ResponseEntity<List<Long>> getJoinedUserGroupIds(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 사용자 정보가 없을 때 처리
            // 예를 들어 로그인 페이지로 리다이렉트하거나 오류 응답을 고려할 수 있습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = user.getEmail();
        List<Long> joinedGroupIds = userGroupService.getJoinedUserGroupIds(email);

        // 디버깅을 위한 로깅
        System.out.println("가입한 그룹 ID 목록: " + joinedGroupIds);

        return ResponseEntity.ok(joinedGroupIds);
    }

    @GetMapping("/bookmark")
    public ResponseEntity<List<Long>> getBookmarkedUserGroupIds(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 사용자 정보가 없을 때 처리
            // 예를 들어 로그인 페이지로 리다이렉트하거나 오류 응답을 고려할 수 있습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = user.getEmail();
        List<Long> bookmarkedGroupIds = userGroupService.getBookmarkedUserGroupIds(email);

        // 디버깅을 위한 로깅
        System.out.println("즐겨찾기한 그룹 ID 목록: " + bookmarkedGroupIds);

        return ResponseEntity.ok(bookmarkedGroupIds);
    }


    @DeleteMapping("/unbookmark/{groupId}")
    public ResponseEntity<String> unbookmarkUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupId){
        userGroupService.unbookmarkUserGroup(user, groupId);
        return ResponseEntity.ok("그룹을 즐겨찾기에서 제거했습니다.");
    }

}

