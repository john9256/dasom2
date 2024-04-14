package com.dasom2.vo;

import java.time.LocalDateTime;

public class MeetingScheduleVO {
	
	String userId;
	LocalDateTime LocalDateTimeEpisode;
	String episode;
	String episodeSelected;
	String location;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getLocalDateTimeEpisode() {
		return LocalDateTimeEpisode;
	}
	public void setLocalDateTimeEpisode(LocalDateTime localDateTimeEpisode) {
		LocalDateTimeEpisode = localDateTimeEpisode;
	}
	public String getEpisode() {
		return episode;
	}
	public void setEpisode(String episode) {
		this.episode = episode;
	}
	public String getEpisodeSelected() {
		return episodeSelected;
	}
	public void setEpisodeSelected(String episodeSelected) {
		this.episodeSelected = episodeSelected;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
