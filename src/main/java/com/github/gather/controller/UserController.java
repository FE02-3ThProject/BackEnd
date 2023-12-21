package com.github.gather.controller;


import com.github.gather.dto.JoinGroupDto;
import com.github.gather.dto.UserInfoDto;
import com.github.gather.dto.request.user.UserLoginRequest;
import com.github.gather.dto.request.user.UserSignupRequest;
import com.github.gather.dto.response.user.UserInfoResponse;
import com.github.gather.dto.response.user.UserLoginResponse;
import com.github.gather.entity.User;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.repository.UserRepository;
import com.github.gather.security.JwtTokenProvider;
import com.github.gather.service.AuthService;
import com.github.gather.service.S3Service;
import com.github.gather.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Tag(name = "User",description = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final S3Service s3Service;

    @Operation(summary = "회원 가입" , description = "회원 가입을 진행합니다.")
    @PostMapping(value = "/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserSignupRequest user) {
        User newUser = userService.signup(user);
        userRepository.save(newUser);
        return ResponseEntity.status(200).body(newUser);
    }


    @GetMapping("/{email}/existsEmail")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.checkEmail(email));
    }


    @GetMapping("/{nickname}/existsNickname")
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname){
        return ResponseEntity.ok(userService.checkNickname(nickname));
    }


    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest user) {
        try {
            UserLoginResponse loginUser = userService.login(user);
            Map<String, String> response = new HashMap<>();
            response.put("email", loginUser.getEmail());
            response.put("nickname", loginUser.getNickname());
            response.put("userRole", loginUser.getUserRole().name());
            response.put("location", loginUser.getLocation().getName());
            response.put("image", loginUser.getImage());
            //TODO: 카테고리 자기소개

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(loginUser.getAccessToken());
            headers.add("refreshtoken", loginUser.getRefreshToken());

            return ResponseEntity.status(200).headers(headers).body(response);
        } catch (Exception e) {
            // 서비스 단에서 발생한 예외를 그대로 반환
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> userLogout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long userId = jwtTokenProvider.findUserIdBytoken(token);
        userService.logout(userId);
        return ResponseEntity.ok("로그아웃 성공");
    }


    @DeleteMapping("/userDelete")
    public ResponseEntity<?> userDelete(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        Long userIdx = jwtTokenProvider.findUserIdBytoken(token);
        userService.deleteUser(userIdx);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String email) {
        try {
            UserInfoResponse userInfo = userService.getUserInfo(email);
            return ResponseEntity.ok(userInfo);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 가입한 모임 조회
    @GetMapping("/joined")
    public ResponseEntity<List<JoinGroupDto>> JoinedGroups(HttpServletRequest request) {
        User user = authService.checkToken(request);
        List<JoinGroupDto> joinedGroups = userService.getJoinedGroups(user);
        return ResponseEntity.ok(joinedGroups);
    }

    @GetMapping("/bookmarked")
    public ResponseEntity<List<JoinGroupDto>> BookmarkedGroups(HttpServletRequest request){
        User user = authService.checkToken(request);
        List<JoinGroupDto> bookMarkedGroups = userService.getBookmarkedGroups(user);
        return ResponseEntity.ok(bookMarkedGroups);
    }

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
