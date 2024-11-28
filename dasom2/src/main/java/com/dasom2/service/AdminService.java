package com.dasom2.service;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasom2.mapper.AdminMapper;
import com.dasom2.mapper.CommonMapper; 

@Service
public class AdminService { 
	
	@Autowired
	AdminMapper adminMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
	// 입력 형식 지정
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 M월 d일 a h시");
	
    // 출력 형식 지정
	private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 a h시");
    
	// 회원 정보 조회
	public List<Map<String, Object>> getUserInfoAdmin(){
		return adminMapper.getUserInfoAdmin();
	}
	
	// 스케줄 조회 
	public List<Map<String, Object>> getScheduleInfoAdmin(){
		return adminMapper.getScheduleInfoAdmin();
	}
	
	// 매칭 정보
	public List<Map<String, Object>> getMatchingInfoAdmin(){
		return adminMapper.getMatchingInfoAdmin();
	}
	
	// admin check
	public boolean checkAdmin(String adminId) {
		if(adminMapper.checkAdmin(adminId) > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// 스케줄 조회 
		public List<Map<String, Object>> getScheduleAdmin(){
			return adminMapper.getScheduleAdmin();
		}
		
	// 로그 조회 
		public List<Map<String, Object>> getLogAdmin(){
			return adminMapper.getLogAdmin();
		}
	
	// 로그 조회 
		public List<Map<String, Object>> getErrorLogAdmin(){
			return adminMapper.getErrorLogAdmin();
		}
			
	// 해당 회원 찬스 증가
	public Map<String, Object> increaseChance(String userId) {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			int count = adminMapper.increaseChance(userId);
			if(count == 1) {
				status.put("status", "increase");
			}
			else {
				status.put("status", "fail");
			}
		} catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
	         status.put("status", "error");
	         throw e;
		}
			return status;
	}
	
	// 해당 회원 찬스 감소
	public Map<String, Object> decreaseChance(String userId) {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			int count = adminMapper.decreaseChance(userId);
				if(count == 1) {
					status.put("status", "decrease");
				}
				else {
					status.put("status", "fail");
				}
		} catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
	         status.put("status", "error");
	         throw e;
		}
			return status;
	}
	
	// 참가 가능 인원 증가
	public Map<String, Object> increaseHeadCount(String episode) {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			int count = adminMapper.increaseHeadCount(episode);
			if(count == 1) {
				status.put("status", "increase");
			}
			else {
				status.put("status", "fail");
			}
		} catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(episode, methodName, e.getMessage());
	         status.put("status", "error");
	         throw e;
		}
			return status;
	}
	
	// 참가 가능 인원 감소
	public Map<String, Object> decreaseHeadCount(String episode) {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			int count = adminMapper.decreaseHeadCount(episode);
				if(count == 1) {
					status.put("status", "decrease");
				}
				else {
					status.put("status", "fail");
				}
		} catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(episode, methodName, e.getMessage());
	         status.put("status", "error");
	         throw e;
		}
			return status;
	}
	
	public Map<String, Object> changePassFlag(String userId, String passFlag) {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			int count = adminMapper.changePassFlag(userId, passFlag);
				if(count == 1) {
					status.put("status", "changed");
				}
				else {
					status.put("status", "fail");
				}
		} catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
	         status.put("status", "error");
		}
				return status;
	}
	
	@Transactional
	public Map<String, Object> deleteSchedule(String episode) {
	    Map<String, Object> status = new HashMap<String, Object>();
	    try {
	        int count = adminMapper.deleteSchedule(episode);
	        adminMapper.deleteAllParticipantByEpisode(episode);
	        if(count == 1) {
	            status.put("status", "deleted");
	        } else {
	            status.put("status", "fail");
	        }
	    } catch (Exception e) {
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        String methodName = stackTrace[1].getMethodName();
	        CommonMapper.insertErrorLog("admin", methodName, e.getMessage());
	        status.put("status", "error");
	        throw e; // 트랜잭션 롤백을 위해 예외를 다시 던짐
	    }
	    return status;
	}
	
	public Map<String, Object> addSchedule(String episode, int headCount, String location, int matchingResultTerm) {
		Map<String, Object> status = new HashMap<String, Object>();
		try {
			int count = adminMapper.addSchedule(episode, headCount, location, matchingResultTerm);
				if(count == 1) {
					status.put("status", "complete");
				}
				else {
					status.put("status", "fail");
				}
		} catch (Exception e) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog("admin", methodName, e.getMessage());
	         status.put("status", "error");
	         throw e;
		}
				return status;
	}
	
	
	@Transactional
	public Map<String, Object> deleteParticipantUserAdmin(String userId, String episode) {
	    Map<String, Object> status = new HashMap<String, Object>();
	    try {
	        int count = adminMapper.deleteParticipantUserAdmin(userId, episode);
	        if(count == 1) {
	            status.put("status", "deleted");
	        } else {
	            status.put("status", "fail");
	        }
	    } catch (Exception e) {
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        String methodName = stackTrace[1].getMethodName();
	        CommonMapper.insertErrorLog("admin", methodName, e.getMessage());
	        status.put("status", "error");
	        throw e; // 트랜잭션 롤백을 위해 예외를 다시 던짐
	    }
	    return status;
	}
	
}

