package com.deepak.auth.app.service;

import com.deepak.auth.app.config.JwtUtil;
import com.deepak.auth.app.config.TokenProperty;
import com.deepak.auth.app.dto.AuthRequest;
import com.deepak.auth.app.dto.TokenResponse;
import com.deepak.auth.app.dto.UserDto;
import com.deepak.auth.app.entity.User;
import com.deepak.auth.app.repo.UserRepository;
import com.deepak.auth.app.utils.AuthenticationFailedException;
import com.deepak.auth.app.utils.ModelMapperUtils;
import com.deepak.auth.app.utils.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginService {

    private final TokenProperty tokenProperty;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(TokenProperty tokenProperty, AuthenticationManager authenticationManager, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.tokenProperty = tokenProperty;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse validateAndGenerateToken(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authentication;

        try {
            authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(), authRequest.getPassword()));
        } catch (AuthenticationException e) {
            log.error("Method Name : validateAndGenerateToken | Message: Authentication Unsuccessful");
            throw new AuthenticationFailedException(e.getMessage(), HttpStatus.BAD_REQUEST.name());
        }
        User user = (User) authentication.getPrincipal();

        String token = null;
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userName", user.getUsername());
            claims.put("name", user.getName());
            claims.put("email", user.getEmailId());
            token = JwtUtil.generateToken(claims, user.getUsername(), tokenProperty.getExpires(),
                    tokenProperty.getPrivateKey(), user.getAuthorities().stream().
                            map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Method Name : validateAndGenerateToken | Message: Error In Token Generation");
            throw new RuntimeException(e.getLocalizedMessage());
        }
        log.info("Method Name : validateAndGenerateToken | Token generated successfully");
        return new TokenResponse("Bearer", tokenProperty.getExpires(), token);
    }

    public User registerUser(UserDto userDto) {
        User user = userRepository.findByEmailId(userDto.getEmailId()).orElse(null);
        if (Objects.nonNull(user)) {
            log.info("User Already Registered : " + user.getEmailId());
            throw new UserException("User Already Register", "USER FOUND!", userDto.getEmailId());

        }
        User mapUser = ModelMapperUtils.map(userDto, User.class);
        mapUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(mapUser);
        log.info("Method Name : registerUser | User registered successfully with ID: {}", savedUser.getId());
        return savedUser;
    }
}
