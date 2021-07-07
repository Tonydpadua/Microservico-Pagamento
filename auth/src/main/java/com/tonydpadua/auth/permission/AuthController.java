package com.tonydpadua.auth.permission;

import com.tonydpadua.auth.jwt.JwtTokenProvider;
import com.tonydpadua.auth.user.User;
import com.tonydpadua.auth.user.UserRepository;
import com.tonydpadua.auth.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml",},
            consumes = {"application/json", "application/xml", "application/x-yaml",})
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            String username = userDTO.getUserName();
            String password = userDTO.getPassword();

            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = this.userRepository.findByUserName(username);

            String token = "";

            if (user != null) {
                token = this.jwtTokenProvider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username not found!");
            }
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return ok(model);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping(value = "/teste")
    public String teste() {
        return "teste";
    }
}
