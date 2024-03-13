package com.dasom2.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
    
	String getMeetingSchedule(String userId);
	
}