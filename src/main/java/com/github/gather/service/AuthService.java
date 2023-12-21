package com.github.gather.service;

import com.github.gather.entity.User;
import com.github.gather.exception.ErrorException;
import com.github.gather.repository.UserRepository;
import com.github.gather.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public User checkToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        String userEmail = jwtTokenProvider.getUserEmail(token);
        return userRepository.findByEmailAndIsDeletedFalse(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
    }
    public User getUser(Principal principal) {
        if (principal == null) {
            throw new ErrorException("인증되지 않은 유저입니다.");
        }
        String email = principal.getName();
        return userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() -> new ErrorException("User not found"));
    }
}
