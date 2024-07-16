package com.dasom2.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KakaoMapper {
    void saveUser(String userId);
    int checkUserExist(String userId);
}