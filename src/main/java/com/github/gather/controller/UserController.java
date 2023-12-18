package com.github.gather.controller;


import com.github.gather.dto.request.UserLoginRequest;
import com.github.gather.dto.request.UserSignupRequest;
import com.github.gather.dto.response.UserLoginResponse;
import com.github.gather.entity.User;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.security.TokenContext;
import com.github.gather.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;


    @Transactional
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



    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest user) {
        UserLoginResponse loginUser = userService.login(user);

        Map<String, String> response = new HashMap<>();
        response.put("email", loginUser.getEmail());
        response.put("nickname", loginUser.getNickname());
        response.put("phoneNumber", loginUser.getPhoneNumber());
        response.put("userRole", loginUser.getUserRole().name());
        response.put("location", loginUser.getLocation().getName());
        response.put("image", loginUser.getImage());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginUser.getAccessToken());
        headers.add("Refresh-Token", loginUser.getRefreshToken());

        return ResponseEntity.status(200).headers(headers).body(response);
    }

    @Transactional
    @PostMapping("/logout")
    public ResponseEntity<?> userLogout() {
        Long userId = TokenContext.getProfileId();
        userService.logout(userId);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @Transactional
    @DeleteMapping("/userDelete")
    public ResponseEntity<?> userDelete(){
        Long userIdx = TokenContext.getProfileId();
        userService.deleteUser(userIdx);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }




}
