package com.tonydpadua.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtProvider {


    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Long expireLength;

    @Qualifier("userService")
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    public String createToken(String userName, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles);

        Date now = new Date();
        Date validate = new Date(now.getTime() + this.expireLength);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validate)
                .signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUserName(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String  resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
