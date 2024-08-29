package com.example.mybatis.controller;

import com.example.mybatis.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.mybatis.dto.UserDto.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterRequestDto());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterRequestDto registerRequest) {
        UserResponseDto user = userService.registerUser(registerRequest);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new LoginRequestDto());
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") LoginRequestDto loginRequest, HttpSession session) {
        UserResponseDto user = userService.login(loginRequest);
        if (user != null) {
            session.setMaxInactiveInterval(3600);
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/login?error";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "user/profile";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}

