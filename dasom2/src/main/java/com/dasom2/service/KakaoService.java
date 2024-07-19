package com.dasom2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.KakaoMapper;

@Service
public class KakaoService {
    
    @Autowired
    private KakaoMapper kakaoMapper;

    public void saveUser(String userId, String profileImageUrl) {
        kakaoMapper.saveUser(userId, profileImageUrl);
    }
    
    public void updateUser(String profileImageUrl) {
    	kakaoMapper.updateUser(profileImageUrl);
    }
    
    public int checkUserExist(String userId) {
    	return kakaoMapper.checkUserExist(userId);
    }
    
    
}

