package com.dasom2.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.MeetingScheduleVO;
import com.dasom2.vo.matchPersonVO;

@Mapper
public interface MainMapper {
    
	List<MeetingScheduleVO> getMeetingSchedule(String userId);
	void insertParticipantUser(String userId, LocalDateTime episode, Boolean episodeSelected);
	void deleteParticipantUser(String userId, LocalDateTime episode, Boolean episodeSelected);
	String checkHeadCount(String userId, LocalDateTime episode);
	String getPassFlagbyUser(String userId);
	List<matchPersonVO> getMatchingInfo(String userId);
	void insertPickUser(String userId, String nickName, String pickType);
	LocalDateTime test();
}