package com.dasom2.vo;

import java.util.Date;

public class LoginLogVO {
	private String userId;
	private String ip;
	private Date createtime;
	private boolean successflag;
	private int count;

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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public boolean isSuccessflag() {
		return successflag;
	}

	public void setSuccessflag(boolean successflag) {
		this.successflag = successflag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}