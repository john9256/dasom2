package com.dasom2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
    
    List<String> getCriteriaData(String criteria);
    
}