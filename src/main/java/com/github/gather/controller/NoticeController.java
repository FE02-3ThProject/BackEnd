package com.github.gather.controller;

import com.github.gather.dto.NoticeDto;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupTableService;
import com.github.gather.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/group")
public class NoticeController {

    private final NoticeService noticeService;
    private final AuthService authService;
    private final GroupTableService groupTableService;

    public NoticeController(NoticeService noticeService, AuthService authService, GroupTableService groupTableService) {
        this.noticeService = noticeService;
        this.authService = authService;
        this.groupTableService = groupTableService;
    }

    // 공지 등록
    @PostMapping("/{groupId}/notice")
    public ResponseEntity<NoticeDto> createNotice(@PathVariable Long groupId, @RequestBody NoticeDto noticeDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        GroupTable group = groupTableService.getGroupById(groupId);
        NoticeDto newNoticeDto = noticeService.createNotice(group, noticeDto, request);
        return ResponseEntity.ok(newNoticeDto);
    }

    // 공지 조회(전체)
    @GetMapping("/{groupId}/notice")
    public List<NoticeDto> getAllNotices(@PathVariable Long groupId) {
        return noticeService.getAllNoticesByGroup(groupId);
    }

    // 공지 조회(특정)
    @GetMapping("/{groupId}/notice/{noticeIdx}")
    public NoticeDto getNotice(@PathVariable Long groupId, @PathVariable Long noticeIdx) {
        return noticeService.getNotice(noticeIdx);
    }

    // 공지 수정
    @PutMapping("/{groupId}/notice/{noticeIdx}")
    public ResponseEntity<NoticeDto> updateNotice(@PathVariable Long groupId, @PathVariable Long noticeIdx, @RequestBody NoticeDto noticeDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        NoticeDto updatedNotice = noticeService.updateNotice(noticeIdx, noticeDto, request);
        return ResponseEntity.ok(updatedNotice);
    }

    // 공지 삭제
    @DeleteMapping("/{groupId}/notice/{noticeIdx}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long groupId, @PathVariable Long noticeIdx, HttpServletRequest request) {
        User user = authService.checkToken(request);
        noticeService.deleteNotice(noticeIdx, request);
        return ResponseEntity.noContent().build();
    }
}

