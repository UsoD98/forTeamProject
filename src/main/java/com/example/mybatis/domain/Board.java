package com.example.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private boolean isDeleted;
    private LocalDateTime createdAt;

    private String writerNickname;
    private int commentCount;

}
