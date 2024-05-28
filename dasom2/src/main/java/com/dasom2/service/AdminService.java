package com.dasom2.service;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.AdminMapper;
import com.dasom2.mapper.CommonMapper; 

@Service
public class AdminService { 
	
	@Autowired
	AdminMapper AdminMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
	// 입력 형식 지정
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 M월 d일 a h시");
	
    // 출력 형식 지정
	private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 a h시");
    
	
	// 회원 정보 조회
	public List<Map<String, Object>> getUserInfoAdmin(){
		return AdminMapper.getUserInfoAdmin();
	}
	
	// 스케줄 조회 
	public List<Map<String, Object>> getScheduleInfoAdmin(){
		return AdminMapper.getScheduleInfoAdmin();
	}
	
	// 매칭 정보
	public List<Map<String, Object>> getMatchingInfoAdmin(){
		return AdminMapper.getScheduleInfoAdmin();
	}
	
		
	
	
}

