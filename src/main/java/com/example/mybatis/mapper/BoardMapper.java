package com.example.mybatis.mapper;

import com.example.mybatis.domain.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO board (title, content, writer_id) VALUES (#{title}, #{content}, #{writerId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Board board);

    @Select("SELECT b.*, u.nickname as writer_nickname FROM board b " +
            "JOIN user u ON b.writer_id = u.id " +
            "WHERE b.id = #{id} AND b.is_deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "writerId", column = "writer_id"),
            @Result(property = "writerNickname", column = "writer_nickname"),
            @Result(property = "createdAt", column = "created_at")
    })
    Board findById(Long id);

    @Select("SELECT b.*, u.nickname as writer_nickname FROM board b " +
            "JOIN user u ON b.writer_id = u.id " +
            "WHERE b.is_deleted = false ORDER BY b.created_at DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "writerId", column = "writer_id"),
            @Result(property = "writerNickname", column = "writer_nickname"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Board> findAll();

    @Select("SELECT b.*, u.nickname as writer_nickname, " +
            "(SELECT COUNT(*) FROM comment c WHERE c.board_id = b.id AND c.is_deleted = false) as comment_count " +
            "FROM board b " +
            "JOIN user u ON b.writer_id = u.id " +
            "WHERE b.is_deleted = false ORDER BY b.created_at DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "writerId", column = "writer_id"),
            @Result(property = "writerNickname", column = "writer_nickname"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "commentCount", column = "comment_count")
    })
    List<Board> findAllWithCommentCount();

    @Update("UPDATE board SET title = #{title}, content = #{content} WHERE id = #{id}")
    int update(Board board);

    @Update("UPDATE board SET is_deleted = true WHERE id = #{id}")
    int softDelete(Long id);



}
