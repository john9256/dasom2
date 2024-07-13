package com.dasom2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dasom2.mapper.KakaoMapper;
import com.dasom2.vo.UserVO;

@Service
public class KakaoService {
    
    @Autowired
    private KakaoMapper kakaoMapper;

    public void saveUser(String userId) {
        kakaoMapper.saveUser(userId);
    }
    
    public int checkUserExist(String userId) {
    	return kakaoMapper.checkUserExist(userId);
    }
    
}

