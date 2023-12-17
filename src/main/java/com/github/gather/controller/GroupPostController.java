package com.github.gather.controller;

import com.github.gather.dto.GroupPostDto;
import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/group")
public class GroupPostController {

    private final GroupPostService groupPostService;
    private final AuthService authService;

    public GroupPostController(GroupPostService groupPostService, AuthService authService) {
        this.groupPostService = groupPostService;
        this.authService = authService;
    }

    // 게시글 등록
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

    // 게시글 조회(전체)
    @GetMapping("/{groupId}/post")
    public ResponseEntity<?> getPosts(@PathVariable Long groupId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        return ResponseEntity.ok(groupPostService.getPosts(groupId));
    }

    // 게시글 조회(특정)
    @GetMapping("/{groupId}/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long groupId, @PathVariable Long postId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        return ResponseEntity.ok(groupPostService.getPost(postId));
    }

    // 게시글 수정
    @PutMapping("/{groupId}/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long groupId, @PathVariable Long postId, @RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        return ResponseEntity.ok(groupPostService.updatePost(postId, groupPostDto));
    }

    // 게시글 삭제
    @DeleteMapping("/{groupId}/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long groupId, @PathVariable Long postId, HttpServletRequest request) {
        User user = authService.checkToken(request);
        groupPostService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
