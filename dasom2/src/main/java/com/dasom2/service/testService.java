package com.dasom2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.testMapper;

@Service
public class testService { 
	
	@Autowired
    testMapper testMapper;
    
    //로그인 아이디 비번 체크
    public String selectTest(){
        return testMapper.selectTest();
    }

    
}

