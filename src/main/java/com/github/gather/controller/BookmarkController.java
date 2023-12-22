package com.github.gather.controller;

import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.BookmarkService;
import com.github.gather.service.group.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Bookmark",description = "즐겨찾기 API")
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final AuthService authService;
    private final GroupService groupService;

    public BookmarkController(BookmarkService bookmarkService, AuthService authService, GroupService groupService) {
        this.bookmarkService = bookmarkService;
        this.authService = authService;
        this.groupService = groupService;
    }

    @Operation(summary = "모임 즐겨찾기 추가,삭제" , description = "모임 즐겨찾기 추가,삭제를 진행합니다.")
    @PostMapping("/{groupId}")
    public ResponseEntity<?> toggleBookmark(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        GroupTable group = groupService.getGroupById(groupId);
        bookmarkService.toggleBookmark(user, group);
        return ResponseEntity.ok().build();
    }
}

