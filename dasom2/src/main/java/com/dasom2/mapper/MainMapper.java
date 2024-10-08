package com.dasom2.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.MeetingScheduleVO;
import com.dasom2.vo.matchPersonVO;

@Mapper
public interface MainMapper {
    
	String getCheckAdmin(String userId);
	String getTicket(String userId);
	List<MeetingScheduleVO> getMeetingSchedule(String userId);
	int checkChace(String userId);
	int minusChance(String userId);
	int plusChance(String userId);
	int checkDuplication(String userId, LocalDateTime LocalDateTimeEpisode);
	String checkContinuity(String userId, LocalDateTime LocalDateTimeEpisode);
	String checkDoublePerDay(String userId, LocalDateTime LocalDateTimeEpisode);
	int insertParticipantUser(String userId, LocalDateTime episode, Boolean episodeSelected);
	int deleteParticipantUser(String userId, LocalDateTime episode, Boolean episodeSelected);
	int checkAbleDelete(String userId, LocalDateTime episode);
	String checkHeadCount(String userId, LocalDateTime episode);
	int checkMatchPickCount(String userId, LocalDateTime episode);
	String getPassFlagbyUser(String userId);
	List<matchPersonVO> getMatchingInfo(String userId);
	List<Map<String, Object>> getParticipantList();
	List<Map<String, Object>> getMatchingResultInfo(String userId);
	List<Map<String, Object>> getMatchingResultInfo2(String userId);
	Map<String, Object> getParticipantCount();
	String getEpisodeByUser(String userId);
	int insertPickUser(String userId, LocalDateTime episode, String nickName);
	int deletePickUser(String userId, LocalDateTime episode, String nickName);
	LocalDateTime test();
	int checkUserInfoExist(String userId);
	String getUserNameByUserId(String userId);
}