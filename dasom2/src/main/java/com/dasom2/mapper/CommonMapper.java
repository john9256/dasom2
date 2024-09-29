package com.dasom2.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
    
    List<Map<String, Object>> getCriteriaData(String criteria);
    void insertErrorLog(String userId, String methodName, String errorMessage);
    int logUserHistory(String userId, String target, String type);
    int logAdminHistory(String adminId, String target, String type);
    List<String> getUserListByEpisode(String episode);
    String getClosestEpisodeByUser(String userId);
}