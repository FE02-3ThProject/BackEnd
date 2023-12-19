package com.github.gather.controller;

import com.github.gather.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/newToken")
    public ResponseEntity<String> refreshToken(HttpServletRequest request , HttpServletResponse response){
        String result = tokenService.refreshToken(request, response);
        return ResponseEntity.ok(result);
    }
}
