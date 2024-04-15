package com.github.gather.service;

import com.github.gather.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sun.security.auth.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String newAccessToken = jwtTokenProvider.refreshAccessToken(token);

            Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
            String userEmail = jwtTokenProvider.getUserPk(token);
            UserPrincipal userPrincipal = new UserPrincipal(userEmail);

            authentication = new UsernamePasswordAuthenticationToken(
                    userPrincipal, authentication.getCredentials(), authentication.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Response 헤더에 새로운 AccessToken을 실어서 보내줄 수 있음
            response.setHeader("NewAccessToken", newAccessToken);
            return "토큰 재발급 완료";
        } else {
            return "검증되지 않은 토큰";
        }
    }
}
