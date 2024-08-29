package com.example.mybatis.mapper;

import com.example.mybatis.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment (content, board_id, writer_id, parent_id, depth) " +
            "VALUES (#{content}, #{boardId}, #{writerId}, #{parentId}, #{depth})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);

    @Select("SELECT c.*, u.nickname as writer_nickname FROM comment c " +
            "JOIN user u ON c.writer_id = u.id " +
            "WHERE c.board_id = #{boardId} AND c.is_deleted = false " +
            "ORDER BY c.parent_id, c.created_at")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "boardId", column = "board_id"),
            @Result(property = "writerId", column = "writer_id"),
            @Result(property = "writerNickname", column = "writer_nickname"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "depth", column = "depth"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Comment> findByBoardId(Long boardId);

    @Update("UPDATE comment SET is_deleted = true WHERE id = #{id}")
    int softDelete(Long id);

    @Select("SELECT COUNT(*) FROM comment WHERE board_id = #{boardId} AND is_deleted = false")
    int countByBoardId(Long boardId);

    @Select("SELECT * FROM comment WHERE id = #{id} AND is_deleted = false")
    Comment findById(Long id);

}
