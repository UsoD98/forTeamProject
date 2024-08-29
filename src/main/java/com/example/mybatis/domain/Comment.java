package com.example.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;
    private String content;
    private Long boardId;
    private Long writerId;
    private Long parentId;
    private int depth;
    private boolean isDeleted;
    private LocalDateTime createdAt;

    private String writerNickname;
    private List<Comment> children;

}
