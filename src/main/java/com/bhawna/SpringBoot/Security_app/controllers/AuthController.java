package com.bhawna.SpringBoot.Security_app.controllers;

import com.bhawna.SpringBoot.Security_app.dtos.LoginDto;
import com.bhawna.SpringBoot.Security_app.dtos.LoginResponseDto;
import com.bhawna.SpringBoot.Security_app.dtos.SignupDto;
import com.bhawna.SpringBoot.Security_app.dtos.UserDto;
import com.bhawna.SpringBoot.Security_app.entities.User;
import com.bhawna.SpringBoot.Security_app.services.AuthService;
import com.bhawna.SpringBoot.Security_app.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto){
        UserDto user = userService.signup(signupDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
        public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
          String refreshToken =   Arrays.stream(request.getCookies())
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the Cookies"));

                LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);
                return ResponseEntity.ok(loginResponseDto);
        }
}
