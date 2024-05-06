package com.dasom2.service;

import java.util.List;
import java.util.Map;

public interface MainServiceInterface {
	
	List<Map<String, Object>> getParticipantList(String userId);
}
