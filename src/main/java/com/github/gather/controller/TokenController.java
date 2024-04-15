package com.github.gather.controller;

import com.github.gather.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "Token",description = "AccessToken 재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @Operation(summary = "AccessToken 재발급" , description = "AccessToken 재발급을 진행합니다.")
    @GetMapping("/newToken")
    public ResponseEntity<String> refreshToken(HttpServletRequest request , HttpServletResponse response){
        String result = tokenService.refreshToken(request, response);
        return ResponseEntity.ok(result);
    }
}
