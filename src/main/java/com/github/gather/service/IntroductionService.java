package com.github.gather.service;

import com.github.gather.dto.request.IntroductionRequest;
import com.github.gather.dto.response.IntroductionResponse;
import org.springframework.stereotype.Service;

@Service
public class IntroductionService {

    public IntroductionResponse getIntroduction(IntroductionRequest request) {
        // 여기에 자기 소개를 생성하는 로직을 추가하세요.
        String selfIntroduction = "안녕하세요! 제 이름은 " + request.getName() + "이고, 나이는 " + request.getAge() + "세입니다. 취미는 " + request.getHobby() + "이며, 반갑습니다!";
        return new IntroductionResponse(selfIntroduction);
    }

}
