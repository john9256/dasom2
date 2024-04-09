package com.dasom2.service;
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
	
	public List<MeetingScheduleVO> getMeetingSchedule(String userId) {
		return MainMapper.getMeetingSchedule(userId);
	}
	
	public Map<String, Object> insertParticipantUser(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		System.out.println(userId);
		System.out.println(episode);
		if(MainMapper.checkHeadCount(userId, episode) != null) {
			System.out.println(MainMapper.checkHeadCount(userId, episode));
			MainMapper.insertParticipantUser(userId, episode, episodeSelected);
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
		MainMapper.deleteParticipantUser(userId, episode, episodeSelected);
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
	
	public List<matchPersonVO> getMatchingInfo(String userId){
		return MainMapper.getMatchingInfo(userId);
	}
	
	
}

