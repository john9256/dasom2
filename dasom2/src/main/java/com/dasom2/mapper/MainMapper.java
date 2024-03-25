package com.dasom2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.MeetingScheduleVO;

@Mapper
public interface MainMapper {
    
	List<MeetingScheduleVO> getMeetingSchedule(String userId);
	void getMeetingSchedule(String userId, String episode, Boolean episodeSelected);
}