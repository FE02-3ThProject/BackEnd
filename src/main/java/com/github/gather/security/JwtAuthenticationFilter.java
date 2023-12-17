package com.github.gather.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        jwtTokenProvider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request); //Request의 헤더에서 토큰을 불러와서 저장

        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰이 유효한지 검사

            // AccessToken이 만료되었다면
            if (!jwtTokenProvider.validateToken(token)) {
                // 여기서 RefreshToken을 사용하여 새로운 AccessToken을 발급받는 로직 추가
                String newAccessToken = jwtTokenProvider.refreshAccessToken(token);

                // 새로운 AccessToken으로 Authentication 객체 생성
                Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Response 헤더에 새로운 AccessToken을 실어서 보내줄 수 있음
                response.setHeader("New-Access-Token", newAccessToken);
            } else {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response); //Custom한 필터 등록
    }
}
