package com.dasom2.service;
import java.util.List;

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
	
	public boolean insertScheduleSelection(String userId, String episode, String episodeSelected) {
		// 스케줄에 인원수가 남으면 참가인원에 insert
		if(MainMapper.checkHeadCount(episode) == null) {
			MainMapper.insertScheduleSelection(userId, episode, episodeSelected);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deleteScheduleSelection(String userId, String episode, String episodeSelected) {
		MainMapper.deleteScheduleSelection(userId, episode, episodeSelected);
		return true;
	}
}

