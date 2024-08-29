package com.example.mybatis.mapper;

import com.example.mybatis.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (email, password, nickname) values ( #{email}, #{password}, #{nickname} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User findById(Long userId);

    @Update("UPDATE user SET is_deleted = true where id = #{userId}")
    int softDelete(Long userId);

    @Update("UPDATE user SET nickname = #{nickname} WHERE id = #{id}")
    int updateNickname(@Param("id") Long id, @Param("nickname") String nickname);

    @Select("SELECT * FROM user WHERE email = #{email} AND password = #{password}")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("SELECT nickname FROM user WHERE id = #{id}")
    String getNicknameById(Long id);

}
