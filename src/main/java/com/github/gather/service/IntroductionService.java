package com.github.gather.service;

import com.github.gather.dto.response.IntroductionResponse;
import com.github.gather.entity.User;
import com.github.gather.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntroductionService {


    private final UserRepository userRepository;

    public IntroductionResponse getIntroduction(User user) {
        // 데이터베이스에서 사용자의 소개를 가져와서 반환
        User userFromDB = userRepository.findById(user.getUserId()).orElse(null);
        String introductionText = (userFromDB != null && userFromDB.getIntroduction() != null)
                ? userFromDB.getIntroduction()
                : null ;

        return new IntroductionResponse(introductionText);
    }
}


