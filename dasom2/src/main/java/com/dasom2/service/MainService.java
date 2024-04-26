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
public class MainService { 
	
	@Autowired
	MainMapper MainMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 M월 d일 a h시");
	
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
		
	}
	
	public Map<String, Object> insertParticipantUserDupliacate(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		Map<String, Object> status = new HashMap<String, Object>();
		
			MainMapper.insertParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
			MainMapper.minusChance(userId);
			status.put("status", "complete");
			return status;
		
	}
	
	public Map<String, Object> deleteParticipantUser(String userId, String episode, Boolean episodeSelected) {
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		// MainMapper.deleteParticipantUserHistory(userId, LocalDateTimeEpisode, episodeSelected);
		MainMapper.deleteParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
		MainMapper.plusChance(userId);
		Map<String, Object> status = new HashMap<String, Object>();
		status.put("status", "cancel");
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
	
	// 매칭 모달 인원 리스트 get
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
			 Map<String, Object> emptyMap = new HashMap<>();
		        emptyMap.put("status", "empty");
		        matchInfoList.add(emptyMap);
		}
		System.out.println(matchInfoList);
		return matchInfoList;
		
	}
	
	
	// 인원 pick
	
	public Map<String, Object> insertMatchPick(String userId, String episode, String nickName) {
        LocalDateTime localDateTimeEpisode = LocalDateTime.parse(episode, formatter);
        Map<String, Object> status = new HashMap<>();
        try {
        	if(MainMapper.checkMatchPickCount(userId, localDateTimeEpisode) > 2) {
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
		 try {MainMapper.deletePickUser(userId, LocalDateTimeEpisode, nickName);
		 } catch (Exception e) {
			 StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	         String methodName = stackTrace[1].getMethodName(); // '1'은 현재 메소드를 가리키는 인덱스입니다.
	         CommonMapper.insertErrorLog(userId, methodName, e.getMessage());
	         status.put("status", "error");
		 }
		 status.put("status", "complete");
		 return status;
		 
	 }
}

