package com.example.mybatis.service;

import com.example.mybatis.domain.User;
import com.example.mybatis.dto.UserDto.*;
import com.example.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserResponseDto registerUser(RegisterRequestDto registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .nickname(registerRequest.getNickname())
                .build();

        userMapper.insert(user);
        return convertToUserResponse(user);
    }

    public UserResponseDto login(LoginRequestDto loginRequest) {
        User user = userMapper.findByEmail(loginRequest.getEmail());
        if( user != null && user.getPassword().equals(loginRequest.getPassword()) ) {
            return convertToUserResponse(user);
        }

        return null;
    }

    public UserResponseDto findUserById(Long userId) {
        User user = userMapper.findById(userId);
        return user != null ? convertToUserResponse(user) : null;
    }

    public boolean deleteUser(Long userId) {
        return userMapper.softDelete(userId) > 0;
    }

    public UserResponseDto updateNickname(Long id, String newNickname) {
        if (userMapper.updateNickname(id, newNickname) > 0) {
            User updatedUser = userMapper.findById(id);
            return convertToUserResponse(updatedUser);
        }
        return null;
    }


    private UserResponseDto convertToUserResponse(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setNickname(user.getNickname());
        responseDto.setCreatedAt(user.getCreatedAt());
        return responseDto;
    }
}
