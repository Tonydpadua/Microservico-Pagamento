package com.tonydpadua.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //TODO Verificar
        JwtTokenFilter customFilter = new JwtTokenFilter();
        httpSecurity.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
