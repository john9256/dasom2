package com.dasom2.vo;

public class UserVO {
	
	String userId;
	String kakaoId;
	String kakaoUuid;
	
	String ip;
	String passFlag;
	String email;
	String phoneNumber;
	int chance;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPassFlag() {
		return passFlag;
	}
	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}
	
	public String getKakaoId() {
		return kakaoId;
	}
	public void setKakaoId(String kakaoId) {
		this.kakaoId = kakaoId;
	}
	public String getKakaoUuid() {
		return kakaoUuid;
	}
	public void setKakaoUuid(String kakaoUuid) {
		this.kakaoUuid = kakaoUuid;
	}
}
