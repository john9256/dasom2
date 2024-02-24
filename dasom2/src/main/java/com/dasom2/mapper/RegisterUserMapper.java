package com.dasom2.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.RegisterUserVO;

@Mapper
public interface RegisterUserMapper {
    
//    List<String> getJobDivisionData();
    int checkUserId(String userId);
    int checkEmail(String email);
    void saveEmailAndToken(Map<String, Object> params);
    String getEmailByToken(String token);
    void updateEmailVerification(String successEmail);
    void insertEmailSendLog(String userId, String email, String currentIp);
    boolean checkEmailVerified(String email, String userId);
    void insertUser(RegisterUserVO user);
}