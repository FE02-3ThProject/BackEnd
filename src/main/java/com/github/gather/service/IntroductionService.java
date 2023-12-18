package com.github.gather.service;

import com.github.gather.dto.response.IntroductionResponse;
import com.github.gather.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntroductionService {

    public IntroductionResponse getIntroduction(User user) {
        String selfIntroduction = "안녕하세요! 제 이름은 " + user.getUsername() + "입니다.";
        return new IntroductionResponse(selfIntroduction);
    }

    public IntroductionResponse getIntroduction() {
        return null;
    }
}
