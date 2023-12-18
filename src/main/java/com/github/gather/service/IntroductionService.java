package com.github.gather.service;

import com.github.gather.dto.request.IntroductionRequest;
import com.github.gather.dto.response.IntroductionResponse;
import com.github.gather.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntroductionService {

    public IntroductionResponse getIntroduction(User user) {
        // 여기에 자기 소개를 생성하는 로직을 추가하세요.
        String selfIntroduction = "안녕하세요! 제 이름은 " + user.getUsername() + "입니다.";
        return new IntroductionResponse(selfIntroduction);
    }

    public IntroductionResponse getIntroduction() {
        return null;
    }
}
