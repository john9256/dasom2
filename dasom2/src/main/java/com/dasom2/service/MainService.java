package com.dasom2.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasom2.mapper.CommonMapper;
import com.dasom2.mapper.MainMapper;
import com.dasom2.vo.MeetingScheduleVO;
import com.dasom2.vo.matchPersonVO;

@Service
public class MainService { 
	
	@Autowired
	MainMapper MainMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
	// 입력 형식 지정
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 M월 d일 a h시").withLocale(Locale.KOREAN);
	
    // 출력 형식 지정
	private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 a h시").withLocale(Locale.KOREAN);
    
	// admin check
	public String getCheckAdmin(String userId) {
		return MainMapper.getCheckAdmin(userId);
	}
	
	// ticket get
	public String getTicket(String userId) {
		if(MainMapper.getTicket(userId) != null) {
			return MainMapper.getTicket(userId);
		}
		else {
			return "0";
		}
	}
	
	// 날짜 선택 GET
	public List<MeetingScheduleVO> getMeetingSchedule(String userId) {
		List<MeetingScheduleVO> meetingScheduleInfo = MainMapper.getMeetingSchedule(userId);
		
		for (MeetingScheduleVO meetingSchedule : meetingScheduleInfo) {
	        String formattedEpisode = meetingSchedule.getLocalDateTimeEpisode().format(formatter);
	        meetingSchedule.setEpisode(formattedEpisode);
	    }
		return meetingScheduleInfo;
	}
	
	// 소개팅 현황 get
	public List<Map<String, Object>> getParticipantList(String userId){
		
		List<Map<String, Object>> participantList = new ArrayList<>();
		
		Map<String, Object> temp = new HashMap<>();
		
		participantList = MainMapper.getParticipantList(userId);
		if(participantList.size() > 0) {
		    for(int i = 0; i < participantList.size(); i ++) {
		        Object yearObj = participantList.get(i).get("year");
		        if (yearObj instanceof Long) {
		            // Long 타입이면 int로 변환
		            int year = ((Long) yearObj).intValue();
		            participantList.get(i).put("year", classifyAge(year));
		        } else if (yearObj instanceof Integer) {
		            // Integer 타입이면 그대로 사용
		            int year = (Integer) yearObj;
		            participantList.get(i).put("year", classifyAge(year));
		        } else {
		            // year 값이 예상하지 못한 타입일 경우 처리 (optional)
		            throw new IllegalArgumentException("Unexpected type for year: " + yearObj.getClass());
		        }
		    }
		}
		return participantList;
		
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public Map<String, Object> insertParticipantUser(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		int duplicateCount = MainMapper.checkDuplication(userId, LocalDateTimeEpisode);
		
		// 연속 참여 체크 - 관리자 test 를 위해 제외
		if(userId == "3609301426" || userId =="3629220993") {
			duplicateCount = 0;
		}
		
		try {
			
			// 참여 가능 횟수 확인
			if(MainMapper.checkChace(userId) <= 0) {
				status.put("status", "noChance");
			}
			// 최대 인원 수 제한
			else if(MainMapper.checkHeadCount(userId, LocalDateTimeEpisode) == null) {
				// MainMapper.insertParticipantUserHistory(userId, LocalDateTimeEpisode, episodeSelected);
				status.put("status", "full");
			}
			// 소개팅 연속 참여 (5일 이내) 불가 (남성일 경우)
			else if(MainMapper.checkContinuity(userId, LocalDateTimeEpisode) != null) {
				status.put("status", "continuity");
			}
			// 하루에 소개팅 두번 연속 참여 불가
			else if(MainMapper.checkDoublePerDay(userId, LocalDateTimeEpisode) != null) {
				status.put("status", "double");
			}
			// 소개팅 참가 인원 중복 배제
			else if(duplicateCount != 0) {
				status.put("status", "duplicate");
				status.put("duplicateCount", duplicateCount);
			}
			else {
				if(MainMapper.minusChance(userId) == 1 && MainMapper.insertParticipantUser(userId, LocalDateTimeEpisode, episodeSelected) == 1){
					CommonMapper.logUserHistory(userId, userId, "participate");
					status.put("status", "complete");
				}
				else {
					status.put("status", "error");
				}
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
	
	@Transactional(rollbackFor = {Exception.class})
	public Map<String, Object> insertParticipantUserDuplicate(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		
			try {
				if(MainMapper.minusChance(userId) == 1) {
					CommonMapper.logUserHistory(userId, userId, "duplicateParticipate");
					MainMapper.insertParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
					status.put("status", "complete");
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
	            throw e;
	        }
			
			return status;
		
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public Map<String, Object> deleteParticipantUser(String userId, String episode, Boolean episodeSelected) {
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		
		try{
			if(MainMapper.checkAbleDelete(userId, LocalDateTimeEpisode) == 0) {
				status.put("status", "expire");
			}
			else if(MainMapper.deleteParticipantUser(userId, LocalDateTimeEpisode, episodeSelected) > 0 
					&& MainMapper.plusChance(userId) == 1
					&& CommonMapper.logUserHistory(userId, userId, "cancelParticipate") == 1
					) {
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
            throw e;
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
	
	public Boolean checkUserInfoExist(String userId) {
		if(MainMapper.checkUserInfoExist(userId) > 0) {
			return false;
		}
		else {
			return true;
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
	
	// 매치 결과 get
	 
	public List<Map<String, Object>> getMatchingResultInfo(String userId){
		
		return MainMapper.getMatchingResultInfo(userId);
		
	}
	
	// 매치 결과 중 본인 좋아 한다는 사람 get
	 
	public List<Map<String, Object>> getMatchingResultInfo2(String userId){
		
		return MainMapper.getMatchingResultInfo2(userId);
		
	}
	
	// GET RECENT EPISODE BY USERID
	
	public String getEpisodeByUser(String userId){
		
		return MainMapper.getEpisodeByUser(userId);
		
	}
	
	// 인원 pick
	@Transactional(rollbackFor = {Exception.class})
	public Map<String, Object> insertMatchPick(String userId, String episode, String nickName) {
        LocalDateTime localDateTimeEpisode = LocalDateTime.parse(episode, formatter);
        Map<String, Object> status = new HashMap<>();
        try {
        	if(MainMapper.checkMatchPickCount(userId, localDateTimeEpisode) > 1) {
        		status.put("status", "full");
        	}
        	else{
        		if(MainMapper.insertPickUser(userId, localDateTimeEpisode, nickName) ==1) {
	        		CommonMapper.logUserHistory(userId, nickName, "pick");
	        		status.put("status", "complete");
        		}
        		else {
        			status.put("status", "error");
        		}
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
	
	@Transactional(rollbackFor = {Exception.class})
	 public Map<String, Object> deleteMatchPick(String userId, String episode, String nickName){
		 LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		 Map<String, Object> status = new HashMap<String, Object>();
		 // MainMapper.insertPickUserHisotry(userId, localDateTimeEpisode, nickName);
		 try {
			 if(MainMapper.deletePickUser(userId, LocalDateTimeEpisode, nickName) == 1) {
				 CommonMapper.logUserHistory(userId, nickName, "cancelPick");
				 status.put("status", "cancel");
			 }
			 else {
				 status.put("status", "error");
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
		
		
		
	 // 초 중 후반 나이대 구분 함수
	 public String classifyAge(int age) {
	        if (age < 10 || age >= 100) {
	            return "연령대 미정";
	        }
	        
	        int decade = (age / 10) * 10;
	        int remainder = age % 10;
	        String ageGroup;

	        if (remainder < 4) {
	            ageGroup = "초";
	        } else if (remainder < 7) {
	            ageGroup = "중";
	        } else {
	            ageGroup = "후";
	        }

	        return decade + "" + ageGroup;
	    }
	 
	 public String getUserNameByUserId(String userId) {
		 return MainMapper.getUserNameByUserId(userId);
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

