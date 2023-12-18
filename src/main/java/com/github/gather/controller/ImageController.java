package com.github.gather.controller;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.service.S3Service;
import com.github.gather.service.group.GroupService;
import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/group")
public class ImageController {

    private final GroupService groupService;
    private final S3Service s3Service;

    public ImageController(GroupService groupService, S3Service s3Service) {
        this.groupService = groupService;
        this.s3Service = s3Service;
    }

    // 모임방 생성
    @PostMapping
    public ResponseEntity<?> createGroup(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("maxMembers") Integer maxMembers,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("locationId") Long locationId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        try {
            // MultipartFile을 java.io.File로 변환
            File convertFile = new File(file.getOriginalFilename());
            convertFile.createNewFile();
            try (FileOutputStream fout = new FileOutputStream(convertFile)) {
                fout.write(file.getBytes());
            }

            // S3에 파일 업로드
            String imageUrl = s3Service.uploadFile(convertFile.getName(), convertFile);

            CreateGroupRequest newGroupRequest = CreateGroupRequest.builder()
                    .title(name)
                    .image(imageUrl)
                    .description(description)
                    .maxMembers(maxMembers)
                    .categoryId(categoryId)
                    .locationId(locationId)
                    .createdAt(LocalDate.now())
                    .build();

            groupService.createGroup(userPrincipal.getName(), newGroupRequest);

        } catch (Exception e) {
            // 에러 처리
            log.error("그룹 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("그룹 생성 중 오류 발생: " + e.getMessage());
        }

        // 업로드 성공 메시지 반환
        return ResponseEntity.ok("업로드 성공");
    }

    // 모임방 수정
    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroupImage(
            @PathVariable("groupId") Long groupId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String title,
            @RequestParam("description") String description,
            @RequestParam("maxMembers") Integer maxMembers,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("locationId") Long locationId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        try {
            // MultipartFile을 java.io.File로 변환
            File convertFile = new File(file.getOriginalFilename());
            convertFile.createNewFile();
            try (FileOutputStream fout = new FileOutputStream(convertFile)) {
                fout.write(file.getBytes());
            }

            // S3에 이미지 업로드
            String imageUrl = s3Service.uploadFile(convertFile.getName(), convertFile);

            // 모임 정보 수정
            UpdateGroupInfoRequest updateGroupInfo = UpdateGroupInfoRequest.builder()
                    .title(title)
                    .image(imageUrl)
                    .description(description)
                    .maxMembers(maxMembers)
                    .categoryId(categoryId)
                    .locationId(locationId)
                    .build();

            groupService.modifyGroupInfo(userPrincipal.getName(), groupId, updateGroupInfo);

        } catch (Exception e) {
            // 에러 처리
            log.error("모임방 이미지 수정 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모임방 이미지 수정 중 오류 발생: " + e.getMessage());
        }

        // 업로드 성공 메시지 반환
        return ResponseEntity.ok("이미지 업로드 성공");
    }
}

