package com.example.mybatis.service;

import com.example.mybatis.domain.Comment;
import com.example.mybatis.dto.CommentDto.*;
import com.example.mybatis.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    // 계층형 x
//    public CommentResponseDto createComment(CommentRequestDto requestDto, Long boardId, Long writerId) {
//        Comment comment = Comment.builder()
//                .content(requestDto.getContent())
//                .boardId(boardId)
//                .writerId(writerId)
//                .parentId(requestDto.getParentId())
//                .depth(requestDto.getParentId() == null ? 0 : 1)
//                .build();
//
//        commentMapper.insert(comment);
//        return convertToCommentResponse(comment);
//    }
    // 계층형 o
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long boardId, Long writerId) {
        Comment parentComment = null;
        int depth = 0;

        if (requestDto.getParentId() != null) {
            parentComment = commentMapper.findById(requestDto.getParentId());
            if (parentComment != null) {
                depth = parentComment.getDepth() + 1;
            }
        }

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .boardId(boardId)
                .writerId(writerId)
                .parentId(requestDto.getParentId())
                .depth(depth)
                .build();

        commentMapper.insert(comment);
        return convertToCommentResponse(comment);
    }

    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentMapper.findByBoardId(boardId);
        return buildCommentHierarchy(comments);
    }

    public boolean deleteComment(Long commentId) {
        return commentMapper.softDelete(commentId) > 0;
    }

    public int getCommentCountByBoardId(Long boardId) {
        return commentMapper.countByBoardId(boardId);
    }

    private List<CommentResponseDto> buildCommentHierarchy(List<Comment> comments) {
        Map<Long, CommentResponseDto> commentMap = new HashMap<>();
        List<CommentResponseDto> rootComments = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto dto = convertToCommentResponse(comment);
            commentMap.put(dto.getId(), dto);

            if (comment.getParentId() == null) {
                rootComments.add(dto);
            } else {
                CommentResponseDto parentDto = commentMap.get(comment.getParentId());
                if (parentDto != null) {
                    if (parentDto.getChildren() == null) {
                        parentDto.setChildren(new ArrayList<>());
                    }
                    parentDto.getChildren().add(dto);
                }
            }
        }

        return rootComments;
    }

    private CommentResponseDto convertToCommentResponse(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setWriterNickname(comment.getWriterNickname());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setDepth(comment.getDepth());
        return dto;
    }
}
