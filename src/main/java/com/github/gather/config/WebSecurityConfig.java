package com.github.gather.config;


import com.github.gather.oauth2.handler.OAuth2LoginFailureHandler;
import com.github.gather.oauth2.handler.OAuth2LoginSuccessHandler;
import com.github.gather.security.JwtAuthenticationFilter;
import com.github.gather.security.JwtTokenProvider;
import com.github.gather.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/ws/**", "/Web.html", "/Web.js", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/api/group/**").permitAll()
                .antMatchers("/api/newToken").permitAll()
                .antMatchers("/api/user-group").permitAll()
                .antMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                .antMatchers("/chat").permitAll()
                .antMatchers("/api/bookmark").permitAll()
                .antMatchers("/chat/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/api/group").permitAll()
                .antMatchers("/api/group/{groupId}").permitAll()
                .antMatchers("/api/user/info").permitAll()
                .antMatchers("/Web.html").permitAll()
                .antMatchers("/Web.js").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/api/group/{groupId}/transferLeader/{newLeaderId}").authenticated()
                .antMatchers("/api/group/{groupId}/kick/{userId}").authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}





