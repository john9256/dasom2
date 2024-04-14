package com.dasom2.vo;

import java.time.LocalDateTime;

public class matchPersonVO {
	
    private String userId;
    private String nickName;
    private String pick;
    private LocalDateTime localDateTimeEpisode;
    private String pickType;
    private LocalDateTime localDateTimeRemainTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPick() {
		return pick;
	}
	public void setPick(String pick) {
		this.pick = pick;
	}
	public LocalDateTime getLocalDateTimeEpisode() {
		return localDateTimeEpisode;
	}
	public void setLocalDateTimeEpisode(LocalDateTime localDateTimeEpisode) {
		this.localDateTimeEpisode = localDateTimeEpisode;
	}
	public String getPickType() {
		return pickType;
	}
	public void setPickType(String pickType) {
		this.pickType = pickType;
	}
	public LocalDateTime getLocalDateTimeRemainTime() {
		return localDateTimeRemainTime;
	}
	public void setLocalDateTimeRemainTime(LocalDateTime localDateTimeRemainTime) {
		this.localDateTimeRemainTime = localDateTimeRemainTime;
	}
    

	
    
    
}