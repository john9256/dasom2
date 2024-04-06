package com.dasom2.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.MainMapper;
import com.dasom2.vo.MeetingScheduleVO;

@Service
public class MainService { 
	
	@Autowired
	MainMapper MainMapper;
	
	public List<MeetingScheduleVO> getMeetingSchedule(String userId) {
		return MainMapper.getMeetingSchedule(userId);
	}
	
	public Map<String, Object> insertScheduleSelection(String userId, String episode, Boolean episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		if(MainMapper.checkHeadCount(episode) != null) {
			MainMapper.insertScheduleSelection(userId, episode, episodeSelected);
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
	
	public Map<String, Object> deleteScheduleSelection(String userId, String episode, Boolean episodeSelected) {
		MainMapper.deleteScheduleSelection(userId, episode, episodeSelected);
		Map<String, Object> status = new HashMap<String, Object>();
		status.put("status", "cancel");
		return status;
	}
	
	public String getPassFlagbyUser(String userId) {
		return MainMapper.getPassFlagbyUser(userId);
	}
}

