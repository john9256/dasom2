package com.dasom2.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dasom2.mapper.CommonMapper;
import com.dasom2.mapper.RegisterUserMapper;
import com.dasom2.vo.RegisterUserVO;

@Service
public class RegisterUserService { 
	
	@Autowired
	RegisterUserMapper RegisterUserMapper;
	
	@Autowired
	CommonMapper CommonMapper;
	
	@Autowired
    private JavaMailSender emailSender;

    
    // 아이디 중복 체크
    public boolean checkUserId(String userId) {
        return RegisterUserMapper.checkUserId(userId) == 0;
    }
    
    // 이메일 중복 체크 - 존재 하면 true 반환
    public boolean checkEmailExist(String email) {
        return RegisterUserMapper.checkEmail(email) > 0;
    }
    
    // 이메일 발송
    public boolean sendEmailVerification(String userId, String email, String token) {
        String subject = "다솜 소개팅 회원가입 이메일 인증";
        String content = "이메일 인증을 위해 아래 링크를 클릭해주세요.\n"
                       + "http://yourdomain.com/emailVerify?token=" + token;
        
        try {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject(subject);
	        message.setText(content);
	        emailSender.send(message);
	        
	        // 토큰과 이메일 저장
	        RegisterUserMapper.saveEmailAndToken(userId, email, token);
	        return true;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	String methodName = e.getStackTrace()[0].getMethodName();
        	CommonMapper.insertErrorLog(methodName, email, e.getMessage());
            return false;
		}
        
    }
    
    // 이메일 인증 
    public boolean verifyEmailByToken(String token) {
    	String successEmail="";
    	successEmail = RegisterUserMapper.getEmailByToken(token);
    	if(!successEmail.equalsIgnoreCase("") && successEmail != null) {
    		try {
    			RegisterUserMapper.updateEmailVerification(successEmail);
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    			String methodName = e.getStackTrace()[0].getMethodName();
    			CommonMapper.insertErrorLog(methodName, successEmail, e.getMessage());
    			return false;
			}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    // 회원 가입시 이메일 인증 여부 확인
    public boolean checkEmailVerified(String email, String userId) {
    	boolean check = false;
    	check = RegisterUserMapper.checkEmailVerified(email, userId);
    		return check;
    }
    
    // 이메일 발송 로그
    public void insertEmailSendLog(String userId, String email, String currentIp) {
    	RegisterUserMapper.insertEmailSendLog(userId, email, currentIp);
    }
    
    
    
    // 유저 등록 서비스
    public boolean registerUser(RegisterUserVO user) {
    	
    	// 암호화
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(encodedPassword);
    	
    	// 저장
        RegisterUserMapper.insertUser(user);
		return true;
    }
    
    public List<String> getResidenceData(){
    	return RegisterUserMapper.getResidenceData();
    }
    
    
}

