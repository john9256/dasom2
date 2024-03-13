package com.dasom2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.MainMapper;

@Service
public class MainService { 
	
	@Autowired
	MainMapper MainMapper;
	
	public String getMeetingSchedule(String userId) {
		return MainMapper.getMeetingSchedule(userId);
	}
	
	// 기준 정보 get
//	public List<Map<String, Object>> getCriteriaData(String criteria) {
//		return CommonMapper.getCriteriaData(criteria);
//		
//	} 
    
}

