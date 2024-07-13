package com.dasom2.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.UserVO;

@Mapper
public interface KakaoMapper {
    void saveUser(UserVO user);
}