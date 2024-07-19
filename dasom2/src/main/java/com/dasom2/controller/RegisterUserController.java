package com.dasom2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasom2.mapper.CommonMapper;
import com.dasom2.service.CommonService;
import com.dasom2.service.RegisterUserService;
import com.dasom2.vo.RegisterUserVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterUserController {
	
	@Autowired 
	RegisterUserService RegisterUserService;
	
	@Autowired
	CommonService CommonService;
	
	@Autowired
	CommonMapper CommonMapper;
	
	// 회원가입 화면
	@GetMapping("/register.user")
	public String registerUser(@RequestParam(value = "param", required = false, defaultValue = "") String param, Model model, HttpSession session) {
		if (session.getAttribute("userId") == null) {
            return "redirect:/login/kakao";
        }
	    if (!param.isEmpty()) {
	    	model.addAttribute("isUpdate", "Y");
	    	
	    } else {
	    	model.addAttribute("isUpdate", "N");
	    }
	    return "registerUser";
	}
	
    
	// 거주지 정보 get
    @ResponseBody
	@GetMapping("/getCriteriaData")
	public List<Map<String, Object>> getResidenceData(@RequestParam("criteria") String criteria) {
		return CommonService.getCriteriaData(criteria);
	}
	
	// 회원 정보 등록
	@PostMapping("/register")
	@Transactional(rollbackFor = {Exception.class})
	
    public String register(RegisterUserVO user, Model model, HttpSession session){
		String userId = (String) session.getAttribute("userId");
		user.setUserId(userId);
		
		if (session.getAttribute("userId") == null) {
            return "redirect:/login/kakao";
        }
		
		// 회원 정보 update
		RegisterUserService.registerUserInfo(user);
		
		// 회원 manage 테이블 insert - 이미 있으면 안함
		if(RegisterUserService.checkUserManageTable(userId)) {
			RegisterUserService.registerUserManage(user.getUserId());
		}
		
			return "redirect:/mainPage"; 
			
	    }
	
	// 개인정보 UPDATE 를 위한 GET
	@ResponseBody
	@GetMapping("/getUserInfoForUpdate")
    public RegisterUserVO getUserInfoForUpdate(Model model, HttpSession session){
		String userId = (String) session.getAttribute("userId");
		return RegisterUserService.getUserInfoForUpdate(userId);
		
	    }
}
