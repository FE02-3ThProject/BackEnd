package com.github.gather.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class MyGroupSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/user/mygroup").authenticated() // 이 주소에 대한 접근 권한 설정
                .anyRequest().permitAll()  // 다른 요청은 모두 허용
                .and().formLogin().permitAll()  // 로그인 페이지는 모두에게 허용
                .and().logout().permitAll();  // 로그아웃은 모두에게 허용
    }
}
