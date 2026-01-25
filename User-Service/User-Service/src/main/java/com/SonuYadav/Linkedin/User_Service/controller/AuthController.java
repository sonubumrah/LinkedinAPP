package com.SonuYadav.Linkedin.User_Service.controller;

import com.SonuYadav.Linkedin.User_Service.dto.LoginRequestDto;
import com.SonuYadav.Linkedin.User_Service.dto.SignupRequestDto;
import com.SonuYadav.Linkedin.User_Service.dto.UserDto;
import com.SonuYadav.Linkedin.User_Service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUP(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.signIn(loginRequestDto);
        return ResponseEntity.ok(token);
    }



}
