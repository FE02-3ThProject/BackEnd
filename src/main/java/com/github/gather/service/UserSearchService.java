package com.github.gather.service;

import com.github.gather.dto.response.UserInfoResponse;
import com.github.gather.entity.User;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    @Autowired
    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoResponse getUserInfo(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));


        // 필요한 정보로 UserInfoResponse 객체를 생성하여 반환
        return new UserInfoResponse(user.getUserId(), user.getEmail(), user.getNickname());
    }
}
