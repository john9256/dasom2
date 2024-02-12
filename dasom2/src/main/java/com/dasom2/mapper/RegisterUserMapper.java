package com.dasom2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterUserMapper {
    
    List<String> getResidenceData();
    List<String> getJobDivisionData();
    int checkUserId(String userId);
    
}