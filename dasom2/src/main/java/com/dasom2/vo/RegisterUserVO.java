package com.dasom2.vo;

import java.util.Date;

public class RegisterUserVO {
    private String userId;
    private String password;
    private String userName;
    private String phoneNumber;
    private String email;
    private String sex;
    private Date birthday;
    private String height;
    private String jobDivision;
    private String residence;
    private String jobResidence;
//    private byte[] idCardImage;
//    private byte[] businessCardImage;
//    private byte[] selfImage;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getJobDivision() {
		return jobDivision;
	}
	public void setJobDivision(String jobDivision) {
		this.jobDivision = jobDivision;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getJobResidence() {
		return jobResidence;
	}
	public void setJobResidence(String jobResidence) {
		this.jobResidence = jobResidence;
	}
//	public byte[] getIdCardImage() {
//		return idCardImage;
//	}
//	public void setIdCardImage(byte[] idCardImage) {
//		this.idCardImage = idCardImage;
//	}
//	public byte[] getBusinessCardImage() {
//		return businessCardImage;
//	}
//	public void setBusinessCardImage(byte[] businessCardImage) {
//		this.businessCardImage = businessCardImage;
//	}
//	public byte[] getSelfImage() {
//		return selfImage;
//	}
//	public void setSelfImage(byte[] selfImage) {
//		this.selfImage = selfImage;
//	}
    
    
}