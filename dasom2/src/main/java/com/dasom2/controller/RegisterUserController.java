package com.dasom2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dasom2.service.CommonService;
import com.dasom2.service.RegisterUserService;
import com.dasom2.vo.RegisterUserVO;

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
    	boolean isValid = RegisterUserService.checkUserIdExist(userId);
        return isValid;
    }
    
    // 이메일 인증
    @ResponseBody
    @PostMapping("/sendEmailVerification")
    public boolean sendEmailVerification(@RequestParam("email") String email) {
    	boolean isVerified = RegisterUserService.sendEmailVerification(email);
        return isVerified;
    }
	
	// 거주지 정보 get
	@GetMapping("/getCriteriaData")
	public List<String> getResidenceData(@RequestParam("criteria") String criteria) {
		return CommonService.getCriteriaData(criteria);
    
	}
	
	// 회원가입 신청
	@PostMapping("/register")
    public String register(RegisterUserVO user, @RequestParam("images") MultipartFile[] images) {
		if (!RegisterUserService.checkUserIdExist(user.getUserId()) || !RegisterUserService.isEmailVerified(user.getEmail())) {
	        return "registrationFailed"; // 검증 실패 시 반환 페이지
	    }
		else {
		if (RegisterUserService.registerUser(user, images)) {
            return "redirect:/login";
	        } else {
	            return "register"; // 실패 시 회원가입 페이지로 돌아감
	        }
		}
    }
	
	
}
