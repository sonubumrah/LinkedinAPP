package com.SonuYadav.Linkedin.User_Service.service;

import com.SonuYadav.Linkedin.User_Service.Utils.PasswordUtil;
import com.SonuYadav.Linkedin.User_Service.dto.LoginRequestDto;
import com.SonuYadav.Linkedin.User_Service.dto.SignupRequestDto;
import com.SonuYadav.Linkedin.User_Service.dto.UserDto;
import com.SonuYadav.Linkedin.User_Service.entity.User;
import com.SonuYadav.Linkedin.User_Service.exception.BadRequestException;
import com.SonuYadav.Linkedin.User_Service.exception.ResourceNotFoundException;
import com.SonuYadav.Linkedin.User_Service.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthRepository authRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        boolean userExists=authRepository.existsByEmail(signupRequestDto.getEmail());
        if(userExists){
            throw new BadRequestException("User already exists with email: "+signupRequestDto.getEmail());
        }
        User user=modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));
        User savedUser=authRepository.save(user);
        log.info("User registered with id: {}", savedUser.getId());
        return modelMapper.map(savedUser, UserDto.class);

    }

    public String signIn(LoginRequestDto loginRequestDto) {
        User user=authRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));
        boolean isPasswordMatch= PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());
        if(!isPasswordMatch){
            throw new BadRequestException("Password is incorrect");
        }
        String token= jwtService.generateAccessToken(user);
        log.info("User logged in with id: {}", user.getId());
        return token;
    }
}
