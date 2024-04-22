package com.dasom2.vo;

import java.time.LocalDateTime;

public class matchPersonVO {
	
    private String pickedUserId;
    private String nickName;
    private String pick;
    private LocalDateTime localDateTimeEpisode;
    private String pickType;
    
	public String getPickedUserId() {
		return pickedUserId;
	}
	public void setpPickedUserId(String pickedUserId) {
		this.pickedUserId = pickedUserId;
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
    
}