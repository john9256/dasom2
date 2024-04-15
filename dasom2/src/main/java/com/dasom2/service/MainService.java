package com.dasom2.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.MainMapper;
import com.dasom2.vo.MeetingScheduleVO;
import com.dasom2.vo.matchPersonVO;

@Service
public class MainService { 
	
	@Autowired
	MainMapper MainMapper;
	
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
		if(MainMapper.checkHeadCount(userId, LocalDateTimeEpisode) != null) {
			MainMapper.insertParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
			Map<String, Object> status = new HashMap<String, Object>();
			status.put("status", "complete");
			return status;
		}
		else {
			Map<String, Object> status = new HashMap<String, Object>();
			status.put("status", "full");
			return status;
		}
		
	}
	
	public Map<String, Object> deleteParticipantUser(String userId, String episode, Boolean episodeSelected) {
		LocalDateTime LocalDateTimeEpisode = LocalDateTime.parse(episode, formatter);
		MainMapper.deleteParticipantUser(userId, LocalDateTimeEpisode, episodeSelected);
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
	
	public List<Map<String, Object>> getMatchingInfo(String userId){
		
		List<Map<String, Object>> matchInfoList = new ArrayList<>();
		Map<String, Object> matchInfoMap = new HashMap<String, Object>();
		
		List<matchPersonVO> matchingInfoList = MainMapper.getMatchingInfo(userId);
		
		System.out.println(matchingInfoList.size());
		if(matchingInfoList.size() != 0) {
			for(int i =0; i < matchingInfoList.size(); i++) {
				matchPersonVO matchingInfo = matchingInfoList.get(i);
				System.out.println(matchingInfo.getNickName());
				matchInfoMap.put("nickName", matchingInfo.getNickName());
				matchInfoList.set(i, matchInfoMap);
			}
			String formattedEpisode = matchingInfoList.get(0).getLocalDateTimeEpisode().format(formatter);
			
			matchInfoList.get(0).put("episode", formattedEpisode);
			matchInfoList.get(0).put("status", "success");
			System.out.println(matchInfoList);
		}
		else {
			 Map<String, Object> emptyMap = new HashMap<>();
		        emptyMap.put("status", "empty");
		        matchInfoList.add(emptyMap);
		}
		return matchInfoList;
		
	}
	
}

