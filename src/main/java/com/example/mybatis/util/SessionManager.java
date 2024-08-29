//package com.example.mybatis.util;
//
//import com.example.mybatis.dto.UserResponseDTO;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SessionManager {
//
//    private static final String USER_ID_KEY = "userId";
//    private static final String USER_NICKNAME_KEY = "userNickname";
//    private static final int SESSION_TIMEOUT = 3600; // 1 hour
//
//    public Long getUserId(HttpSession session) {
//        return (Long) session.getAttribute(USER_ID_KEY);
//    }
//
//    public void createUserSession(HttpSession session, UserResponseDTO user) {
//        session.setMaxInactiveInterval(SESSION_TIMEOUT);
//        session.setAttribute(USER_ID_KEY, user.getId());
//        session.setAttribute(USER_NICKNAME_KEY, user.getNickname());
//    }
//
//    public void updateUserSession(HttpSession session, UserResponseDTO user) {
//        session.setAttribute(USER_NICKNAME_KEY, user.getNickname());
//    }
//
//    public void expireUserSession(HttpSession session, HttpServletResponse response) {
//        session.invalidate();
//        expireSessionCookie(response);
//    }
//
//    private void expireSessionCookie(HttpServletResponse response) {
//        Cookie sessionCookie = new Cookie("JSESSIONID", null);
//        sessionCookie.setMaxAge(0);
//        sessionCookie.setPath("/");
//        response.addCookie(sessionCookie);
//    }
//
//    public boolean isLoggedIn(HttpSession session) {
//        return session.getAttribute(USER_ID_KEY) != null;
//    }
//
//    public String getUserNickname(HttpSession session) {
//        return (String) session.getAttribute(USER_NICKNAME_KEY);
//    }
//}