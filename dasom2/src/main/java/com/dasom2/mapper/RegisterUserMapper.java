package com.dasom2.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.RegisterUserVO;

@Mapper
public interface RegisterUserMapper {
    
    void registerUserInfo(RegisterUserVO user);
    void insertUserManage(String userId);
    RegisterUserVO getUserInfoForUpdate(String userId);
    int checkUserManageTable(String userId);
}