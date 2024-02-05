package com.dasom2.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface testMapper {
    
    String selectTest();
    
}