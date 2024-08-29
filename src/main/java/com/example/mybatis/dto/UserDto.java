package com.example.mybatis.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class UserDto {

    @Data
    public static class RegisterRequestDto {
        private String email;
        private String password;
        private String nickname;
    }

    @Data
    public static class LoginRequestDto {
        private String email;
        private String password;
    }

    @Data
    public static class UserResponseDto {
        private Long id;
        private String email;
        private String nickname;
        private LocalDateTime createdAt;
    }
}
