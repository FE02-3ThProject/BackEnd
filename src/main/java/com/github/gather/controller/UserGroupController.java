package com.github.gather.controller;

import com.github.gather.dto.response.bookMarkResponse;
import com.github.gather.entity.*;
import com.github.gather.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;


    @GetMapping("/joined")
    public ResponseEntity<List<Long>> getJoinedUserGroups(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<GroupTable> joinedUserGroups = userGroupService.getJoinedUserGroups(email); // 수정된 부분
        List<Long> groupIdList = joinedUserGroups.stream()
                .map(GroupTable::getGroupId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupIdList);
    }


    @GetMapping("/bookmark")
    public ResponseEntity<List<Long>> getBookmarkedUserGroups(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        List<Bookmark> bookmarkedGroups = userGroupService.getBookmarkedUserGroups(email);
        List<Long> groupIdList = bookmarkedGroups.stream()
                .map(Bookmark::getGroupTable)
                .map(GroupTable::getGroupId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupIdList);
    }


    @DeleteMapping("/unbookmark/{groupId}")
    public ResponseEntity<String> unbookmarkUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupId){
        userGroupService.unbookmarkUserGroup(user, groupId);
        return ResponseEntity.ok("그룹을 즐겨찾기에서 제거했습니다.");
    }
}
