package com.dasom2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dasom2.vo.RegisterUserVO;

@Mapper
public interface RegisterUserMapper {
    
    List<String> getResidenceData();
    List<String> getJobDivisionData();
    int checkUserId(String userId);
    int checkEmail(String email);
    void saveEmailAndToken(String userId, String email, String token);
    String getEmailByToken(String token);
    void updateEmailVerification(String successEmail);
    void insertEmailSendLog(String userId, String email, String currentIp);
    boolean checkEmailVerified(String email, String userId);
    void insertUser(RegisterUserVO user);
}