package com.github.gather.oauth2.handler;

import com.github.gather.entity.Role.UserRole;
import com.github.gather.oauth2.CustomOAuth2User;
import com.github.gather.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        try {
//            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//            loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
//        } catch (Exception e) {
//            throw e;
//        }
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == UserRole.GUEST) {
                String accessToken = jwtTokenProvider.createAccessTokenOAuth(oAuth2User.getEmail());
                response.addHeader(jwtTokenProvider.getAccessHeader(), "Bearer " + accessToken);
//                response.sendRedirect("/"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

                jwtTokenProvider.sendAccessAndRefreshToken(response, accessToken, null);
            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
//                response.sendRedirect("/"); // 리다이렉트
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtTokenProvider.createAccessTokenOAuth(oAuth2User.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshTokenOAuth(oAuth2User.getEmail());
        response.addHeader(jwtTokenProvider.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtTokenProvider.getRefreshHeader(), "Bearer " + refreshToken);

        jwtTokenProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);
    }


}
