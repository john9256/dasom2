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
	
	public int logAdminHistory(String adminId, String targetUser, String type) {
		try {
			return CommonMapper.logAdminHistory(adminId, targetUser, type);
		}
		catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
            CommonMapper.insertErrorLog(adminId, methodName, e.getMessage());
			throw e;
		}
	}
	
	public List<String> getUserListByEpisode(String episode) {
		return CommonMapper.getUserListByEpisode(episode);
	}
    
	// 유저의 가장 최근 episode 값을 가져옴
	public String getClosestEpisodeByUser(String userId) {
		return CommonMapper.getClosestEpisodeByUser(userId);
	}
	
}

