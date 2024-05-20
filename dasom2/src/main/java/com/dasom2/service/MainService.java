package com.dasom2.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.CommonMapper;
import com.dasom2.mapper.MainMapper;
import com.dasom2.vo.MeetingScheduleVO;
import com.dasom2.vo.matchPersonVO;

@Service
public class MainService implements MainServiceInterface { 
	
	@Autowired
	MainMapper MainMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
	// 입력 형식 지정
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 M월 d일 a h시");
	
    // 출력 형식 지정
	private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 a h시");
    
	public List<MeetingScheduleVO> getMeetingSchedule(String userId) {
		List<MeetingScheduleVO> meetingScheduleInfo = MainMapper.getMeetingSchedule(userId);
		
		for (MeetingScheduleVO meetingSchedule : meetingScheduleInfo) {
	        String formattedEpisode = meetingSchedule.getLocalDateTimeEpisode().format(formatter);
	        meetingSchedule.setEpisode(formattedEpisode);
	    }
		return meetingScheduleInfo;
	}
	
	public Map<String, Object> insertParticipantUser(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		int duplicateCount = MainMapper.checkDuplication(userId, LocalDateTimeEpisode);
		
		try {
			
			// 참여 가능 횟수 확인
			if(MainMapper.checkChace(userId) <= 0) {
				status.put("status", "noChance");
				return status;
			}
			// 최대 인원 수 제한
			else if(MainMapper.checkHeadCount(userId, LocalDateTimeEpisode) == null) {
				// MainMapper.insertParticipantUserHistory(userId, LocalDateTimeEpisode, episodeSelected);
				status.put("status", "full");
				return status;
			}
			// 소개팅 연속 참여 불가
			else if(MainMapper.checkContinuity(userId, LocalDateTimeEpisode) != null) {
				status.put("status", "continuity");
				return status;
			}
			// 소개팅 참가 인원 중복 배제
			else if(duplicateCount != 0) {
				status.put("status", "duplicate");
				status.put("duplicateCount", duplicateCount);
				return status;
			}
			else {
				MainMapper.insertParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
				MainMapper.minusChance(userId);
				status.put("status", "complete");
				return status;
			}
        	
        } catch (Exception e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
            CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
            status.put("status", "error");
        }
		
		return status;
		
		
	}
	
	public Map<String, Object> insertParticipantUserDupliacate(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		
			try {
				MainMapper.insertParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
				MainMapper.minusChance(userId);
				status.put("status", "complete");
			}
			catch (Exception e) {
	            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	            String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	            CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
	            status.put("status", "error");
	        }
			
			return status;
		
	}
	
	public Map<String, Object> deleteParticipantUser(String userId, String episode, Boolean episodeSelected) {
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		
		try{
			if(MainMapper.deleteParticipantUser(userId, LocalDateTimeEpisode, episodeSelected) > 0) {
				MainMapper.plusChance(userId);
				status.put("status", "cancel");
			}
			else {
				status.put("status", "error");
			}
		}
		catch (Exception e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
            CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
            status.put("status", "error");
        }
		return status;
	}
	
	public Boolean getPassFlagbyUser(String userId) {
		if(MainMapper.getPassFlagbyUser(userId) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// 두근두근 매칭 모달 인원 리스트 get
	public List<Map<String, Object>> getMatchingInfo(String userId){
		
		List<Map<String, Object>> matchInfoList = new ArrayList<>();
		
		List<matchPersonVO> matchingInfoList = MainMapper.getMatchingInfo(userId);
		matchPersonVO matchingInfoVo;
		
		if(matchingInfoList.size() != 0) {
			for(int i =0; i < matchingInfoList.size(); i++) {
				matchingInfoVo = matchingInfoList.get(i);
				Map<String, Object> matchInfoMap = new HashMap<String, Object>();
				matchInfoMap.put("nickName", matchingInfoVo.getNickName());
				matchInfoMap.put("episode", matchingInfoVo.getLocalDateTimeEpisode().format(formatter));
				matchInfoMap.put("pick", matchingInfoVo.getPick());
				matchInfoList.add(i, matchInfoMap);
			}
			matchInfoList.get(0).put("status", "success");
		}
		else {
			 return null;
		}
		return matchInfoList;
		
	}
	
	
	// 인원 pick
	
	public Map<String, Object> insertMatchPick(String userId, String episode, String nickName) {
        LocalDateTime localDateTimeEpisode = LocalDateTime.parse(episode, formatter);
        Map<String, Object> status = new HashMap<>();
        try {
        	if(MainMapper.checkMatchPickCount(userId, localDateTimeEpisode) > 1) {
        		status.put("status", "full");
        	}
        	else{
        		MainMapper.insertPickUser(userId, localDateTimeEpisode, nickName);
        		status.put("status", "complete");
        	}
        	
        } catch (Exception e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
            CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
            status.put("status", "error");
        }
        
        return status;
    }
	
	 public Map<String, Object> deleteMatchPick(String userId, String episode, String nickName){
		 LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		 Map<String, Object> status = new HashMap<String, Object>();
		 // MainMapper.insertPickUserHisotry(userId, localDateTimeEpisode, nickName);
		 try {
			 MainMapper.deletePickUser(userId, LocalDateTimeEpisode, nickName);
			 status.put("status", "cancel");
		 } catch (Exception e) {
			 StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
	         status.put("status", "error");
		 }
		 
		 return status;
		 
	 }
	 
	// 소개팅 현황 get
	 @Override
		public List<Map<String, Object>> getParticipantList(String userId){
			
			List<Map<String, Object>> participantList = new ArrayList<>();
			
			Map<String, Object> temp = new HashMap<>();
			
			participantList = MainMapper.getParticipantList();
			if(participantList.size() > 0) {
				for(int i = 0; i < participantList.size(); i ++) {
					
					participantList.get(i).put("year", classifyAge((int)participantList.get(i).get("year")));
				}
			}
			return participantList;
			
		}
	
	 // 초 중 후반 나이대 구분 함수
	 public String classifyAge(int age) {
	        if (age < 10 || age >= 100) {
	            return "연령대 미정";
	        }
	        
	        int decade = (age / 10) * 10;
	        int remainder = age % 10;
	        String ageGroup;

	        if (remainder < 4) {
	            ageGroup = "초반";
	        } else if (remainder < 7) {
	            ageGroup = "중반";
	        } else {
	            ageGroup = "후반";
	        }

	        return decade + "대 " + ageGroup;
	    }
	 
	 public class TypeChecker {
		    public static void checkType(Object obj) {
		        if (obj instanceof String) {
		            System.out.println("The object is a String.");
		        } else if (obj instanceof Integer) {
		            System.out.println("The object is an Integer.");
		        } else if (obj instanceof Double) {
		            System.out.println("The object is a Double.");
		        } else if (obj instanceof Boolean) {
		            System.out.println("The object is a Boolean.");
		        } else {
		            System.out.println("Unknown type: " + obj.getClass().getName());
		        }
		    }
		}
}

