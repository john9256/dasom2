package com.dasom2.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.CommonMapper;

@Service
public class CommonService { 
	
	@Autowired
	CommonMapper CommonMapper;
	
	// 기준 정보 get
	public List<Map<String, Object>> getCriteriaData(String criteria) {
		return CommonMapper.getCriteriaData(criteria);
		
	}
	
	public int stackAdminHistory(String adminId, String targetUser, String type) {
		return CommonMapper.stackAdminHistory(adminId, targetUser, type);
	}
    
}

