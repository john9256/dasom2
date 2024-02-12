package com.dasom2.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dasom2.mapper.RegisterUserMapper;
import com.dasom2.vo.RegisterUserVO;

@Service
public class RegisterUserService { 
	
	@Autowired
	RegisterUserMapper RegisterUserMapper;
	
	@Autowired
    private JavaMailSender emailSender;

    
    // 아이디 중복 체크
    public boolean checkUserIdExist(String userId) {
        return RegisterUserMapper.checkUserId(userId) > 0;
    }
    
    // 이메일 인증
    public boolean sendEmailVerification(String email) {
        // 이메일 인증 로직 구현
        String verificationLink = "http://yourdomain.com/verify?email=" + email; // 예시 링크
        emailService.sendSimpleMessage(email, "이메일 인증", "아래 링크를 클릭해주세요: " + verificationLink);
        return true;
    }
    
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@example.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }
    
    // 유저 등록 서비스
    public boolean registerUser(RegisterUserVO user, MultipartFile[] images) {
        try {
            // 파일 저장 로직
            if (images != null) {
                for (int i = 0; i < images.length; i++) {
                    MultipartFile file = images[i];
                    if (!file.isEmpty()) {
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                        Files.write(path, bytes);

                        // 파일 경로를 UserVO에 설정 (예시로만 보여줌, 실제 구현에 따라 달라질 수 있음)
                        switch (i) {
                            case 0:
                                user.setIdCardImagePath(path.toString());
                                break;
                            case 1:
                                user.setBusinessCardImagePath(path.toString());
                                break;
                            case 2:
                                user.setSelfImagePath(path.toString());
                                break;
                        }
                    }
                }
            }

            // 데이터베이스에 사용자 정보와 파일 경로 저장
            userMapper.insertUser(user);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> getResidenceData(){
    	return RegisterUserMapper.getResidenceData();
    }
    
    
}

