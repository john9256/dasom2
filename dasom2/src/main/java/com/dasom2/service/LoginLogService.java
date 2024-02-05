package com.dasom2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.LoginLogMapper;
import com.dasom2.vo.LoginLogVO;

@Service
public class LoginLogService {
    @Autowired
    LoginLogMapper loginLogMapper;

    public void insertLog(LoginLogVO log) {
        loginLogMapper.insertLog(log);
    }

    public Integer getLatestCountByIp(String ip) {
        return loginLogMapper.getLatestCountByIp(ip);
    }
    
    public void updateCount(String userId) {
        loginLogMapper.updateCount(userId);
    }
}
