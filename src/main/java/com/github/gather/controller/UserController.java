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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping(value = "/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserSignupRequest user) {
        User newUser = userService.signup(user);
        userRepository.save(newUser);
        return ResponseEntity.status(200).body(newUser);
    }

    @Operation(summary = "이메일 중복 체크",description = "이메일 중복 체크를 진행합니다.")
    @GetMapping("/{email}/existsEmail")
    public ResponseEntity<Boolean> checkEmail(
            @Parameter(description = "체크할 이메일", required = true)
            @PathVariable String email){
        return ResponseEntity.ok(userService.checkEmail(email));
    }

    @Operation(summary = "닉네임 중복 체크",description = "닉네임 중복 체크를 진행합니다.")
    @GetMapping("/{nickname}/existsNickname")
    public ResponseEntity<Boolean> checkNickname(
            @Parameter(description = "체크할 닉네임", required = true)
            @PathVariable String nickname){
        return ResponseEntity.ok(userService.checkNickname(nickname));
    }

    @Operation(summary = "로그인" , description = "로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
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

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(loginUser.getAccessToken());
            headers.add("refreshtoken", loginUser.getRefreshToken());

            return ResponseEntity.status(200).headers(headers).body(response);
        } catch (Exception e) {
            // 서비스 단에서 발생한 예외를 그대로 반환
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @Operation(summary = "로그아웃" , description = "로그아웃을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> userLogout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        Long userId = jwtTokenProvider.findUserIdBytoken(token);
        userService.logout(userId);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @Operation(summary = "회원탈퇴" , description = "회원탈퇴를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/userDelete")
    public ResponseEntity<?> userDelete(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        Long userIdx = jwtTokenProvider.findUserIdBytoken(token);
        userService.deleteUser(userIdx);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }


    @Operation(summary = "유저정보" , description = "유저정보를 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저정보 요청 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestParam String email) {
        try {
            UserInfoResponse userInfo = userService.getUserInfo(email);
            return ResponseEntity.ok(userInfo);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "가입한 모임" , description = "가입한 모임들을 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가입한 모임 요청 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/joined")
    public ResponseEntity<List<JoinGroupDto>> JoinedGroups(HttpServletRequest request) {
        User user = authService.checkToken(request);
        List<JoinGroupDto> joinedGroups = userService.getJoinedGroups(user);
        return ResponseEntity.ok(joinedGroups);
    }

    @Operation(summary = "즐겨찾기한 모임" , description = "즐겨찾기한 모임들을 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기한 모임 요청 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/bookmarked")
    public ResponseEntity<List<JoinGroupDto>> BookmarkedGroups(HttpServletRequest request){
        User user = authService.checkToken(request);
        List<JoinGroupDto> bookMarkedGroups = userService.getBookmarkedGroups(user);
        return ResponseEntity.ok(bookMarkedGroups);
    }

    @Operation(summary = "회원정보 수정" , description = "회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
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
