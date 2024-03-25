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
	
	public void updateScheduleSelection(String userId, String episode, Boolean episodeSelected) {
		MainMapper.getMeetingSchedule(userId, episode, episodeSelected);
	}
	
    
}

