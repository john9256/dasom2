package com.sw.service;
import com.sw.mapper.LoginMapper;
import com.sw.vo.testVO;
import com.sw.vo.UserVO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
    LoginMapper loginMapper;
    
    //로그인 아이디 비번 체크
    public String validateUser(String userId, String password){
        return loginMapper.findByUsernameAndPassword(userId, password);
    }
    
    //설문조사 완료 여부 체크
    public String surveyCheck(String userId) {
    	return loginMapper.surveyCheck(userId);
    }
    
}

