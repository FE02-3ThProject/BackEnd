package com.github.gather.controller;

import com.github.gather.dto.UserInfoDto;
import com.github.gather.service.S3Service;
import com.github.gather.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserImageController {

    private final UserService userService;
    private final S3Service s3Service;

    public UserImageController(UserService userService, S3Service s3Service) {
        this.userService = userService;
        this.s3Service = s3Service;
    }

    // 유저 정보 수정
    @PutMapping("/info")
    public ResponseEntity<UserInfoDto> updateUserInfo(
            @RequestParam("nickname") String nickname,
            @RequestParam("locationId") Long locationId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("introduction") String introduction
    ) {
        try {
            // MultipartFile을 java.io.File로 변환
            File convertFile = new File(image.getOriginalFilename());
            convertFile.createNewFile();
            try (FileOutputStream fout = new FileOutputStream(convertFile)) {
                fout.write(image.getBytes());
            }

            // S3에 이미지 업로드
            String imageUrl = s3Service.uploadFile(image.getOriginalFilename(), convertFile);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            UserInfoDto userInfoDto = UserInfoDto.builder()
                    .nickname(nickname)
                    .locationId(locationId)
                    .categoryId(categoryId)
                    .image(imageUrl)
                    .introduction(introduction)
                    .build();

            UserInfoDto updatedUserInfo = userService.updateUserInfo(email, userInfoDto);

            // 업로드 성공 메시지 반환
            return ResponseEntity.ok(updatedUserInfo);
        } catch (Exception e) {
            // 에러 처리
            log.error("유저 정보 수정 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



