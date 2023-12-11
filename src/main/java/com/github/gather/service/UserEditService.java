package com.github.gather.service;

import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.response.UserEditResponse;
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

    public UserEditResponse editUser(UserEditRequest userEditRequest) {
        // 이메일을 기반으로 회원 정보 조회
        User user = userRepository.findByEmail(userEditRequest.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("해당 이메일을 가진 회원을 찾을 수 없습니다."));


        // 수정한 필드 업데이트
        user.setNickname(userEditRequest.getNewNickname());
        user.setEmail(userEditRequest.getNewEmail());
        user.setPassword(userEditRequest.getNewPassword());
        user.setPhoneNumber(userEditRequest.getNewPhoneNumber());
        user.setImage(userEditRequest.getNewImage());
        user.setLocationId(userEditRequest.getNewLocation());

        userRepository.save(user);

        UserEditResponse userEditResponse = new UserEditResponse();
        userEditResponse.setMessage("회원 정보가 성공적으로 수정되었습니다.");

        return userEditResponse;
    }
}

