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
	List<Map<String, Object>> getLogAdmin();
	List<Map<String, Object>> getErrorLogAdmin();
	int checkAdmin(String adminId);
	int increaseChance(String userId);
	int decreaseChance(String userId);
	int increaseHeadCount(String userId);
	int decreaseHeadCount(String userId);
	int changePassFlag(@Param("userId") String userId, @Param("passFlag") String passFlag);
	int deleteSchedule(String episode);
	int deleteAllParticipantByEpisode(String episode);
	int addSchedule(String episode, int headCount, String location);

}