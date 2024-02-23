package com.dasom2.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dasom2.service.CommonService;
import com.dasom2.service.RegisterUserService;
import com.dasom2.vo.EmailVerificationTokenVO;
import com.dasom2.vo.RegisterUserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterUserController {
	
	@Autowired 
	RegisterUserService RegisterUserService;
	
	@Autowired
	CommonService CommonService;
	
	// 회원가입 화면
	@GetMapping("/register.user")
	public String registerUser() {
		return "registerUser";
	}
	
	// 아이디 중복 체크
    @ResponseBody
    @GetMapping("/checkUserId")
    public boolean checkUserId(@RequestParam("userId") String userId) {
    	boolean isValid = RegisterUserService.checkUserId(userId);
        return isValid;
    }
    
    // 이메일 발송
    @ResponseBody
    @PostMapping("/sendEmailVerification")
    public boolean sendEmailVerification(@RequestParam("email") String email, @RequestParam("userId") String userId, HttpSession session, HttpServletRequest request) {
    	// 이메일 중복 체크
    	boolean isValid = RegisterUserService.checkEmailExist(email);
    	
    	// 이메일 발송 로그
    	String currentIp = request.getRemoteAddr();
    	RegisterUserService.insertEmailSendLog(userId, email, currentIp);
    	
    	if(!isValid) {
	    	String token = UUID.randomUUID().toString();
	    	return RegisterUserService.sendEmailVerification(userId, email, token);
    	}
    	else {
    		return false;
    	}
    }
	
    // 이메일 인증 
    @GetMapping("/emailVerify")
    public String verifyEmailByToken(@RequestParam String token, Model model) {
    	boolean successFlag = false;
    	successFlag = RegisterUserService.verifyEmailByToken(token);
        if(successFlag) {
        	model.addAttribute("successFlag", "Y");
        	return "verificationDivision";
        }
        else {
        	model.addAttribute("successFlag", "N");
        	return "verificationDivision";
        }
    }
    
    
	// 거주지 정보 get
    @ResponseBody
	@GetMapping("/getCriteriaData")
	public List<String> getResidenceData(@RequestParam("criteria") String criteria) {
		return CommonService.getCriteriaData(criteria);
	}
	
	
	// 회원가입 신청
	@PostMapping("/register")
    public String register(RegisterUserVO user

    		) {
		if (RegisterUserService.checkUserId(user.getUserId()) || RegisterUserService.checkEmailVerified(user.getUserId(), user.getEmail())) {
	        RegisterUserService.registerUser(user);
			return "redirect:/loginPage"; 
	    }
		else {
			return "registerFail";
		}
    }
	
	
}
