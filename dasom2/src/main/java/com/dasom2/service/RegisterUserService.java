package com.dasom2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.CommonMapper;
import com.dasom2.mapper.RegisterUserMapper;
import com.dasom2.vo.RegisterUserVO;

@Service
public class RegisterUserService { 
	
	@Autowired
	RegisterUserMapper RegisterUserMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
//	@Autowired
//    private JavaMailSender emailSender;
	
    // 유저 인적사항 등록 , 이력 적치
    public void registerUserInfo(RegisterUserVO user) {
    	RegisterUserMapper.registerUserInfoHistory(user);
        RegisterUserMapper.registerUserInfo(user);
    }
    
    // 유저 관리 데이터 등록
    public void registerUserManage(String userId) {
    	RegisterUserMapper.insertUserManage(userId);
    }
    
    public RegisterUserVO getUserInfoForUpdate(String userId) {
    	return RegisterUserMapper.getUserInfoForUpdate(userId);
    }
    
    // user_manage 테이블에 이미 있는지 확인
    public boolean checkUserManageTable(String userId) {
    	if(RegisterUserMapper.checkUserManageTable(userId) > 0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
}

