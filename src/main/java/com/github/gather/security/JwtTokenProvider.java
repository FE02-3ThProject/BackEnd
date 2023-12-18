package com.github.gather.security;

import com.github.gather.entity.RefreshToken;
import com.github.gather.entity.TokenBlacklist;
import com.github.gather.entity.User;
import com.github.gather.repositroy.RefreshTokenRepository;
import com.github.gather.repositroy.TokenBlacklistRepository;
import com.github.gather.repositroy.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    //   1. yml에 적었던 문자열로 된 토큰을 @Value를 통해서 가져옴
    @Value("secret-key")
    private String secretKey;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;

//    // 토큰 유효시간 168 시간(7일)
//    private long tokenValidTime = 1440 * 60 * 7 * 1000L;
    private long accessTokenValidTime = 30 * 60 * 1000L; // 30분

    // 토큰 유효시간 7일
    private long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L; // 7일


    private final CustomUserDetailsService customUserDetailsService;

    //    2. 가져온 문자열로 된 토큰을 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

//     JWT 토큰 생성 (이메일)

//    public String createToken(User loginUser) {
//        Claims claims = Jwts.claims().setSubject(loginUser.getEmail());
//        claims.put("id", loginUser.getUserId());
//        claims.put("role", loginUser.getUserRole()); // 정보는 key/value 쌍으로 저장됩니다.
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간
//                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 유효 시간
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘
//                .compact();
//    }
    // Refresh Token 생성
    public String createRefreshToken(User loginUser) {
        Claims claims = Jwts.claims().setSubject(loginUser.getEmail());
        claims.put("id", loginUser.getUserId());
        claims.put("role", loginUser.getUserRole());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Access Token 생성
    public String createAccessToken(User loginUser) {
        Claims claims = Jwts.claims().setSubject(loginUser.getEmail());
        claims.put("id", loginUser.getUserId());
        claims.put("role", loginUser.getUserRole());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }




    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
//        인증된 사용자의 아이디, 비밀번호, 권한을 파라미터로 받음 - customUserDetailsService.loadUserByUsername
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); //"" : 비밀번호는 SpringSecurity에서 자동 처리
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token) {
//        토큰을 파라미터로 받고 getBody().getSubject()를 하면 사용자의 아이디 반환 -> customUserDetailsService.loadUserByUsername -> getAuthentication
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 사용자가 요청했을 때 헤더에서 토큰을 가져오는 메서드 : HttpServletRequest(Header (token))  "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String findEmailBytoken(String token) {
        // JWT 토큰을 디코딩하여 페이로드를 얻기
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        // "userId" 클레임의 값을 얻기
        return claims.isEmpty() ? null : claims.get("sub", String.class);
    }

    public Long findUserIdBytoken(String token) {
        // JWT 토큰을 디코딩하여 페이로드를 얻기
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        // "userId" 클레임의 값을 얻기
        return claims.isEmpty() ? null : claims.get("id", Long.class);
    }

    public String findRoleBytoken(String token) {
        // JWT 토큰을 디코딩하여 페이로드를 얻기
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        // "userId" 클레임의 값을 얻기
        return claims.isEmpty() ? null : claims.get("role", String.class);
    }

    public String refreshAccessToken(String refreshToken) {
        // RefreshToken의 유효성 검사
        if (!validateToken(refreshToken)) {
            throw new BadCredentialsException("유효하지 않은 RefreshToken입니다.");
        }

        // RefreshToken에서 UserId 추출
        Long userId = findUserIdBytoken(refreshToken);

        // UserId로 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 사용자의 RefreshToken 조회
        RefreshToken storedRefreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new BadCredentialsException("RefreshToken이 존재하지 않습니다."));

        // 저장된 RefreshToken과 요청된 RefreshToken이 일치하는지 검사
        if (!storedRefreshToken.getToken().equals(refreshToken)) {
            throw new BadCredentialsException("유효하지 않은 RefreshToken입니다.");
        }

        // 새로운 AccessToken 발급
        String newAccessToken = createAccessToken(user);

        return newAccessToken;
    }

    public void invalidateRefreshToken(String refreshToken) {
        // 블랙리스트에 토큰이 없는 경우에만 추가
        if (!tokenBlacklistRepository.existsById(refreshToken)) {
            // 토큰을 블랙리스트에 추가
            TokenBlacklist tokenBlacklist = new TokenBlacklist(refreshToken,new Date());
            tokenBlacklistRepository.save(tokenBlacklist);

            // 만료 시간을 현재 시간 이전으로 설정
            // (RefreshToken의 경우, 새로 발급하는 시점에서의 만료 시간 갱신 필요)
        }
    }

}
