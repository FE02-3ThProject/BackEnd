package com.github.gather.service;

import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.response.UserEditResponse;
import com.github.gather.entity.Location;
import com.github.gather.entity.User;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserEditService {

    private final UserRepository userRepository;

    @Autowired
    public UserEditService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEditResponse editUserInfo(Long userId, UserEditRequest userEditRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));


        // userEditRequest로 전달된 정보로 User 정보를 수정
        existingUser.setNickname(userEditRequest.getNickname());
        existingUser.setEmail(userEditRequest.getEmail());
        existingUser.setPassword(userEditRequest.getPassword());
        existingUser.setPhoneNumber(userEditRequest.getPhoneNumber());
        existingUser.setImage(userEditRequest.getImage());
        existingUser.setLocationId(userEditRequest.getLocation());


        // 수정된 정보를 저장
        User editUser = userRepository.save(existingUser);

        // 수정된 정보를 Response 객체로 변환
        return convertToResponse(editUser);
    }

    private UserEditResponse convertToResponse(User user) {
        UserEditResponse response = new UserEditResponse();
        response.setUserId(user.getUserId());
        response.setNickname(user.getNickname());
        response.setPassword(user.getPassword());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setImage(user.getImage());
        response.setLocation(user.getLocationId());

        return response;
    }
}

