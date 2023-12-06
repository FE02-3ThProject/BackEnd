package com.github.gather.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity

public class UserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/edit").authenticated() // /api/user/edit 엔드포인트는 인증된 사용자만 접근 가능
                .anyRequest().permitAll() // 다른 요청은 모두 허용
                .and()
                .httpBasic(); // 기본적인 HTTP 기반 인증 사용
    }
}
