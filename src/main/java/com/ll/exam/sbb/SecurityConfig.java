package com.ll.exam.sbb;

import com.ll.exam.sbb.user.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//시큐리티 설정
@RequiredArgsConstructor
@Configuration  // 스프링의 환경설정 파일임을 의미하는 어노테이션, 스프링 시큐리티의 설정
@EnableWebSecurity  // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션
public class SecurityConfig {

    private final UserSecurityService userSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 모든 경로
                .antMatchers("/**")
                // 허용한다.
                .permitAll()
                // 문맥의 끝
                .and()
                // csrf에 대해서 말하겠다. 밑에 부터는 h2 설정
                .csrf()
                // 밑에 주소를 무시하겠다.
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                // 폼 로그인을 내가 사용하겠다.
                .formLogin()
                // 로그인 페이지 url GetMapping
                .loginPage("/user/login")
                // 로그인 성공시 url PostMapping
                .defaultSuccessUrl("/")
                .and()
                // 로그아웃
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        return http.build();
    }

    // 스프링 시스템에 객체를 등록한다.
    // @Configuration 라는 어노테이션을 가진 클래스에서만 사용가능하다.

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}