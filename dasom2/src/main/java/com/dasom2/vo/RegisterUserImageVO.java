package com.dasom2.vo;

import java.util.Date;

public class RegisterUserImageVO {
   
	private String userId;
    private String idCardPath;
    private String businessCardPath;
    private String selfImagePath;
    
    public RegisterUserImageVO(String userId, String idCardPath, String businessCardPath, String selfImagePath) {
        this.userId = userId;
        this.idCardPath = idCardPath;
        this.businessCardPath = businessCardPath;
        this.selfImagePath = selfImagePath;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdCardPath() {
		return idCardPath;
	}

	public void setIdCardPath(String idCardPath) {
		this.idCardPath = idCardPath;
	}

	public String getBusinessCardPath() {
		return businessCardPath;
	}

	public void setBusinessCardPath(String businessCardPath) {
		this.businessCardPath = businessCardPath;
	}

	public String getSelfImagePath() {
		return selfImagePath;
	}

	public void setSelfImagePath(String selfImagePath) {
		this.selfImagePath = selfImagePath;
	}
    
    
	
    
}