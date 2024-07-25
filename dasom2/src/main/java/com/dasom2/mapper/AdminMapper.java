package com.dasom2.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    
	List<Map<String, Object>> getUserInfoAdmin();
	List<Map<String, Object>> getScheduleInfoAdmin();
	List<Map<String, Object>> getMatchingInfoAdmin();
	List<Map<String, Object>> getScheduleAdmin();
	int checkAdmin(String adminId);
	int increaseChance(String userId);
	int decreaseChance(String userId);
	int changePassFlag(@Param("userId") String userId, @Param("passFlag") String passFlag);
	int deleteSchedule(String episode);
	int deleteAllParticipantByEpisode(String episode);
	int addSchedule(String episode, int headCount, String location);
//	List<MeetingScheduleVO> getMeetingSchedule(String userId);
//	int checkChace(String userId);
//	void minusChance(String userId);
//	void plusChance(String userId);
//	int checkDuplication(String userId, LocalDateTime LocalDateTimeEpisode);
//	String checkContinuity(String userId, LocalDateTime LocalDateTimeEpisode);
//	void insertParticipantUser(String userId, LocalDateTime episode, Boolean episodeSelected);
//	int deleteParticipantUser(String userId, LocalDateTime episode, Boolean episodeSelected);
//	String checkHeadCount(String userId, LocalDateTime episode);
//	int checkMatchPickCount(String userId, LocalDateTime episode);
//	String getPassFlagbyUser(String userId);
//	List<matchPersonVO> getMatchingInfo(String userId);
//	List<Map<String, Object>> getParticipantList();
//	Map<String, Object> getParticipantCount();
//	void insertPickUser(String userId, LocalDateTime episode, String nickName);
//	void deletePickUser(String userId, LocalDateTime episode, String nickName);
//	LocalDateTime test();
}