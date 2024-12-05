package com.deepak.auth.app.controller;

import com.deepak.auth.app.dto.AuthRequest;
import com.deepak.auth.app.dto.TokenResponse;
import com.deepak.auth.app.dto.UserDto;
import com.deepak.auth.app.entity.User;
import com.deepak.auth.app.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/login")
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> login(@Validated @RequestBody AuthRequest authRequest) {
        TokenResponse tokenResponse = loginService.validateAndGenerateToken(authRequest);
        log.info("Message: Login Successfully: ");
        return ResponseEntity.ok(tokenResponse);

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Validated @RequestBody UserDto userDto) {
        User savedUser = loginService.registerUser(userDto);
        log.info("Message: User Registration Successfully: ");
        return ResponseEntity.ok(savedUser);
    }
}
