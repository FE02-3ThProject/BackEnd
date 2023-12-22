package com.github.gather.controller;

import com.github.gather.dto.GroupPostDto;
import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Tag(name = "GroupPost",description = "모임 게시물 API")
@RestController
@RequestMapping("/api/group")
public class GroupPostController {

    private final GroupPostService groupPostService;
    private final AuthService authService;

    public GroupPostController(GroupPostService groupPostService, AuthService authService) {
        this.groupPostService = groupPostService;
        this.authService = authService;
    }

    @Operation(summary = "게시글 등록" , description = "게시물 등록을 진행합니다.")
    @PostMapping("/{groupId}/post")
    public ResponseEntity<?> createPost(@PathVariable Long groupId, @RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        GroupPostDto newGroupPostDto = new GroupPostDto(
                null,
                groupId,
                user.getEmail(),
                groupPostDto.getTitle(),
                groupPostDto.getContent(),
                LocalDate.now()
        );
        GroupPostDto responseDto = groupPostService.createPost(newGroupPostDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "게시글 전체 조회" , description = "게시글 전체 조회를 진행합니다.")
    @GetMapping("/{groupId}/post")
    public ResponseEntity<?> getPosts(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        return ResponseEntity.ok(groupPostService.getPosts(groupId));
    }

    @Operation(summary = "특정 게시글 조회" , description = "특정 게시글 조회를 진행합니다.")
    // 게시글 조회(특정)
    @GetMapping("/{groupId}/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long groupId, @PathVariable Long postId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        return ResponseEntity.ok(groupPostService.getPost(postId));
    }

    @Operation(summary = "게시글 수정" , description = "게시글 수정을 진행합니다.")
    @PutMapping("/{groupId}/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long groupId, @PathVariable Long postId, @RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        return ResponseEntity.ok(groupPostService.updatePost(postId, groupPostDto));
    }

    @Operation(summary = "게시글 삭제" , description = "게시글 삭제를 진행합니다.")
    @DeleteMapping("/{groupId}/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long groupId, @PathVariable Long postId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupPostService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
