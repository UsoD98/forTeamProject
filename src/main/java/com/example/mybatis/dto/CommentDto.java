package com.example.mybatis.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {

    @Data
    public static class CommentRequestDto {
        private String content;
        private Long parentId;
    }

    @Data
    public static class CommentResponseDto {
        private Long id;
        private String content;
        private String writerNickname;
        private LocalDateTime createdAt;
        private int depth;
        private List<CommentResponseDto> children;
    }

}
