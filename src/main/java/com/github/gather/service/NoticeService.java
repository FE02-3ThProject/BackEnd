package com.github.gather.service;

import com.github.gather.dto.NoticeDto;
import com.github.gather.entity.GroupMember;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.NoticeEntity;
import com.github.gather.entity.Role.GroupMemberRole;
import com.github.gather.entity.User;
import com.github.gather.exception.ExceptionMessage;
import com.github.gather.repositroy.NoticeRepository;
import com.github.gather.repositroy.group.GroupMemberRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final AuthService authService;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupTableService groupTableService;

    public NoticeService(NoticeRepository noticeRepository, AuthService authService, GroupMemberRepository groupMemberRepository, GroupTableService groupTableService) {  // 수정
        this.noticeRepository = noticeRepository;
        this.authService = authService;
        this.groupMemberRepository = groupMemberRepository;
        this.groupTableService = groupTableService;
    }

    // 공지 등록
    public NoticeDto createNotice(GroupTable groupId, NoticeDto noticeDto, HttpServletRequest request) {
        User user = authService.checkToken(request);
        GroupMember groupMember = groupMemberRepository.findByUserIdAndGroupId(user, groupId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.USER_NOT_PART_OF_GROUP));
        if (groupMember.getRole() != GroupMemberRole.LEADER) {
            throw new IllegalArgumentException(ExceptionMessage.ONLY_LEADER_CAN_POST_NOTICE);
        }

        NoticeEntity notice = NoticeEntity.builder()
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .createAt(LocalDate.now())
                .groupId(groupId)
                .userId(user)
                .build();
        noticeRepository.save(notice);
        return NoticeDto.builder()
                .noticeIdx(notice.getNoticeIdx())
                .title(notice.getTitle())
                .content(notice.getContent())
                .userId(user.getUserId())
                .createAt(notice.getCreateAt())
                .build();
    }

    // 공지 전체 조회
    public List<NoticeDto> getAllNoticesByGroup(Long groupId) {
        GroupTable group = groupTableService.getGroupById(groupId);
        List<NoticeEntity> noticeEntities = noticeRepository.findByGroupId(group);
        return noticeEntities.stream()
                .map(notice -> NoticeDto.builder()
                        .noticeIdx(notice.getNoticeIdx())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .userId(notice.getUserId().getUserId())
                        .createAt(notice.getCreateAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 특정 공지 조회
    public NoticeDto getNotice(Long noticeIdx) {
        NoticeEntity notice = noticeRepository.findById(noticeIdx)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOTICE_NOT_FOUND));
        return NoticeDto.builder()
                .noticeIdx(notice.getNoticeIdx())
                .title(notice.getTitle())
                .content(notice.getContent())
                .userId(notice.getUserId().getUserId())
                .createAt(notice.getCreateAt())
                .build();
    }

    // 공지 수정
    public NoticeDto updateNotice(Long noticeIdx, NoticeDto noticeDto, HttpServletRequest request) {
        User user = authService.checkToken(request);

        NoticeEntity notice = noticeRepository.findById(noticeIdx).orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOTICE_NOT_FOUND));
        GroupTable groupTable = notice.getGroupId();

        GroupMember groupMember = groupMemberRepository.findByUserIdAndGroupId(user, groupTable)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.USER_NOT_PART_OF_GROUP));

        if (groupMember.getRole() != GroupMemberRole.LEADER) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTH_TO_MODIFY_NOTICE);
        }

        NoticeEntity updatedNotice = noticeRepository.save(NoticeEntity.builder()
                .noticeIdx(noticeIdx)
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .createAt(notice.getCreateAt())
                .groupId(notice.getGroupId())
                .userId(user)
                .build());

        return NoticeDto.builder()
                .noticeIdx(updatedNotice.getNoticeIdx())
                .title(updatedNotice.getTitle())
                .content(updatedNotice.getContent())
                .userId(updatedNotice.getUserId().getUserId())
                .createAt(updatedNotice.getCreateAt())
                .build();
    }


    // 공지 삭제
    public void deleteNotice(Long noticeIdx, HttpServletRequest request) {
        User user = authService.checkToken(request);

        NoticeEntity notice = noticeRepository.findById(noticeIdx).orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOTICE_NOT_FOUND));
        GroupTable groupTable = notice.getGroupId();

        GroupMember groupMember = groupMemberRepository.findByUserIdAndGroupId(user, groupTable)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.USER_NOT_PART_OF_GROUP));

        if (groupMember.getRole() != GroupMemberRole.LEADER) {
            throw new IllegalArgumentException(ExceptionMessage.NO_AUTH_TO_DELETE_NOTICE);
        }

        noticeRepository.deleteById(noticeIdx);
    }
}
