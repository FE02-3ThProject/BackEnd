package com.github.gather.controller;

import com.github.gather.dto.response.BookMarkResponse;
import com.github.gather.dto.response.GroupTableResponse;
import com.github.gather.entity.*;
import com.github.gather.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;


//    @GetMapping("/joined")
//    public ResponseEntity<List<GroupTableResponse>> getJoinedUserGroups(@AuthenticationPrincipal User user) {
//        String email = user.getEmail();
//        List<GroupTable> joinedUserGroups = userGroupService.getJoinedUserGroups(email);
//
//        // Logging for debugging
//        System.out.println("Joined User Groups: " + joinedUserGroups);
//
//        // Convert GroupTable to GroupTableDto
//        List<GroupTableResponse> groupTableDtos = joinedUserGroups.stream()
//                .map(GroupTableResponse::from)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(groupTableDtos);
//    }

    @GetMapping("/joined")
    public ResponseEntity<List<GroupTableResponse>> getJoinedUserGroups(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 사용자 정보가 없는 경우에 대한 처리
            // 예를 들어, 로그인 페이지로 리다이렉트 또는 에러 응답 등을 고려할 수 있습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = user.getEmail();
        List<GroupTable> joinedUserGroups = userGroupService.getJoinedUserGroups(email);

        // Logging for debugging
        System.out.println("Joined User Groups: " + joinedUserGroups);

        // Convert GroupTable to GroupTableDto
        List<GroupTableResponse> groupTableDtos = joinedUserGroups.stream()
                .map(GroupTableResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(groupTableDtos);
    }





//    @GetMapping("/bookmark")
//    public ResponseEntity<List<BookMarkResponse>> getBookmarkedUserGroups(@AuthenticationPrincipal User user) {
//        String email = user.getEmail();
//        List<Bookmark> bookmarks = userGroupService.getBookmarkedUserGroups(email);
//
//        // Logging for debugging
//        System.out.println("Bookmarked User Groups: " + bookmarks);
//
//        // Bookmark을 BookmarkDto로 변환
//        List<BookMarkResponse> bookmarkDtos = bookmarks.stream()
//                .map(BookMarkResponse::from)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(bookmarkDtos);
//    }

    @GetMapping("/bookmark")
    public ResponseEntity<List<BookMarkResponse>> getBookmarkedUserGroups(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 사용자 정보가 없는 경우에 대한 처리
            // 예를 들어, 로그인 페이지로 리다이렉트 또는 에러 응답 등을 고려할 수 있습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = user.getEmail();
        List<Bookmark> bookmarks = userGroupService.getBookmarkedUserGroups(email);

        // Logging for debugging
        System.out.println("Bookmarked User Groups: " + bookmarks);

        // Bookmark을 BookmarkDto로 변환
        List<BookMarkResponse> bookmarkDtos = bookmarks.stream()
                .map(BookMarkResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookmarkDtos);
    }


    @DeleteMapping("/unbookmark/{groupId}")
    public ResponseEntity<String> unbookmarkUserGroup(@AuthenticationPrincipal User user, @PathVariable Long groupId){
        userGroupService.unbookmarkUserGroup(user, groupId);
        return ResponseEntity.ok("그룹을 즐겨찾기에서 제거했습니다.");
    }

}

