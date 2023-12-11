package com.github.gather.service;

import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.entity.Location;
import com.github.gather.entity.User;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;


    public UserInfoResponse getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));


        // 필요한 정보로 UserInfoResponse 객체를 생성하여 반환
        return new UserInfoResponse(user.getUserId(), user.getNickname(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getImage(), user.getLocationId());

    }
}


//    private Long userId;
//    private String nickname;
//    private String email;
//    private String password;
//    private String phoneNumber;
//    private String image;
//    private Location locationId;