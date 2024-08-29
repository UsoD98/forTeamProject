package com.example.mybatis.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    @Data
    public static class CreateBoardRequestDto {
        private String title;
        private String content;
    }

    @Data
    public static class UpdateBoardRequestDto {
        private String title;
        private String content;
    }

    @Data
    public static class BoardResponseDto {
        private Long id;
        private String title;
        private String content;
        private String writerNickname;
        private LocalDateTime createdAt;
        private int commentCount;
    }

}
