package com.example.mybatis.service;
import com.example.mybatis.dto.UserLoginRequestDTO;
import com.example.mybatis.dto.UserRequestDTO;
import com.example.mybatis.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;


    @Test
    public void 회원가입() {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("test@example.com");
        requestDTO.setPassword("password123");
        requestDTO.setName("Test User");
        requestDTO.setNickname("tester");
        requestDTO.setGender("M");

        UserResponseDTO responseDTO = userService.registerUser(requestDTO);
        System.out.println("responseDTO = " + responseDTO);

        assertNotNull(responseDTO);
        assertEquals("test@example.com", responseDTO.getEmail());
        assertEquals("Test User", responseDTO.getName());
    }

    @Test
    public void 회원정보수정() {

        UserRequestDTO updateDTO = new UserRequestDTO();
        updateDTO.setName("Updated User");
        updateDTO.setNickname("updatedNick");
        updateDTO.setGender("F");

        UserResponseDTO updatedUser = userService.updateUser(16L, updateDTO);

        assertNotNull(updatedUser);
        assertEquals("Updated User", updatedUser.getName());
        assertEquals("updatedNick", updatedUser.getNickname());
        assertEquals("F", updatedUser.getGender());

    }

    @Test
    void 회원조회() {
        UserResponseDTO user = userService.findUser(16L);
        System.out.println("user = " + user);
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void 회원로그인() {
        UserLoginRequestDTO loginDTO = new UserLoginRequestDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password123");

        UserResponseDTO loggedInUser = userService.login(loginDTO);

        assertNotNull(loggedInUser);
        assertEquals("test@example.com", loggedInUser.getEmail());
    }

    @Test
    void 회원전체조회() {
        List<UserResponseDTO> users = userService.findAllUsers();
        System.out.println("users = " + users);
    }

}
