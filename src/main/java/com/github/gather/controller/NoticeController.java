package com.github.gather.controller;

import com.github.gather.dto.NoticeDto;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.service.AuthService;
import com.github.gather.service.GroupTableService;
import com.github.gather.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "GroupNotice",description = "모임 공지 API")
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

    @Operation(summary = "공지사항 등록" , description = "공지사항 등록을 진행합니다.")
    @PostMapping("/{groupId}/notice")
    public ResponseEntity<NoticeDto> createNotice(@PathVariable Long groupId, @RequestBody NoticeDto noticeDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        GroupTable group = groupTableService.getGroupById(groupId);
        NoticeDto newNoticeDto = noticeService.createNotice(group, noticeDto, request);
        return ResponseEntity.ok(newNoticeDto);
    }

    @Operation(summary = "전체 공지사항 조회" , description = "전체 공지사항 조회를 진행합니다.")
    @GetMapping("/{groupId}/notice")
    public List<NoticeDto> getAllNotices(@PathVariable Long groupId) {
        return noticeService.getAllNoticesByGroup(groupId);
    }

    @Operation(summary = "특정 공지사항 조회" , description = "특정 공지사항 조회를 진행합니다.")
    @GetMapping("/{groupId}/notice/{noticeIdx}")
    public NoticeDto getNotice(@PathVariable Long groupId, @PathVariable Long noticeIdx) {
        return noticeService.getNotice(noticeIdx);
    }

    @Operation(summary = "공지사항 수정" , description = "공지사항 수정을 진행합니다.")
    @PutMapping("/{groupId}/notice/{noticeIdx}")
    public ResponseEntity<NoticeDto> updateNotice(@PathVariable Long groupId, @PathVariable Long noticeIdx, @RequestBody NoticeDto noticeDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        NoticeDto updatedNotice = noticeService.updateNotice(noticeIdx, noticeDto, request);
        return ResponseEntity.ok(updatedNotice);
    }

    @Operation(summary = "공지사항 삭제" , description = "공지사항 삭제를 진행합니다.")
    @DeleteMapping("/{groupId}/notice/{noticeIdx}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long groupId, @PathVariable Long noticeIdx, HttpServletRequest request) {
        User user = authService.checkToken(request);
        noticeService.deleteNotice(noticeIdx, request);
        return ResponseEntity.noContent().build();
    }
}

