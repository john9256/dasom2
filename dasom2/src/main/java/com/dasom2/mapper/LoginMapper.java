package com.dasom2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("SELECT user_id FROM user_table WHERE user_id = #{userId} AND password = #{password} AND useflag = 'Y'")
    String findByUsernameAndPassword(String userId, String password);
    
    @Select("SELECT COUNT(*) FROM survey_check WHERE user_id = #{userId} AND compFlag = 'Y'")
    String surveyCheck(String userId);
}