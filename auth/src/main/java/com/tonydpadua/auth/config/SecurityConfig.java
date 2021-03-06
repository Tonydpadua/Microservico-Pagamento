package com.tonydpadua.auth.config;

import com.tonydpadua.auth.jwt.JwtConfigurer;
import com.tonydpadua.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] PUBLIC_MATCHES={
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHES_POST={
            "/login"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,PUBLIC_MATCHES_POST).permitAll()
                .antMatchers(PUBLIC_MATCHES).permitAll()
                .antMatchers("/h2-console/login.do").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(this.jwtTokenProvider));
    }
}
