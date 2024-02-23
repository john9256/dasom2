package com.dasom2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.LoginMapper;

@Service
public class LoginService {
	
	@Autowired
    LoginMapper loginMapper;
    
    //로그인 비번 체크
    public String findPasswordByUserId(String userId){
        return loginMapper.findPasswordByUserId(userId);
    }
    
}

