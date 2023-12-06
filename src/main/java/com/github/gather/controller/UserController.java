package com.github.gather.controller;

import com.github.gather.dto.UserDTO;
import com.github.gather.dto.request.UserLoginRequest;
import com.github.gather.dto.request.UserSignupRequest;
import com.github.gather.dto.response.UserLoginResponse;
import com.github.gather.entity.User;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
        headers.setBearerAuth(loginUser.getToken());

        return ResponseEntity.status(200).headers(headers).body(response);
    }


    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUserInfo(@RequestParam String email) {
        // email 값을 사용하여 정보를 조회 로직을 수행
        // 이 부분에서 userService를 이용하여 실제 로직을 수행할 것
        UserDTO userinfo = userService.getUserInfoByEmail(email);

        if (userinfo != null) {
            // 정상적으로 사용자 정보를 가져왔을 경우 200으로 OK응답을 반환
            return ResponseEntity.ok(userinfo);
        } else {
            // 사용자 정보를 찾을 수 없는 경우 404 Not Found 응답을 반환
            return ResponseEntity.notFound().build();
        }

    }
}
