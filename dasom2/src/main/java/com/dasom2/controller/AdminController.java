package com.dasom2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasom2.service.AdminService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	// 관리자 화면
	@GetMapping("/adminPage")
    public String adminPage(Model model, HttpSession session) {
    	
		if (session.getAttribute("userId") == "ksw") {
            return "adminPage";
        }
		return "mainPage";
    }
	
	// 유저 정보 get
	@ResponseBody
    @PostMapping("/getUserInfoAdmin")
    public List<Map<String, Object>> getUserInfoAdmin(HttpSession session) {
		
		if (session.getAttribute("userId").equals("aa")) {
	            return adminService.getUserInfoAdmin();
	    }
	    return null;
    }
	
	// 스케줄 정보 get
	@ResponseBody
    @PostMapping("/getScheduleInfoAdmin")
    public List<Map<String, Object>> getScheduleInfoAdmin(HttpSession session) {
		
		if (session.getAttribute("userId") == "ksw") {
            return adminService.getScheduleInfoAdmin();
        }
		return null;
    }
	
	// 스케줄별 매칭 정보 get
	@ResponseBody
    @PostMapping("/getMatchingInfoAdmin")
    public List<Map<String, Object>> getMatchingInfoAdmin(HttpSession session) {
		
		if (session.getAttribute("userId") == "ksw") {
            return adminService.getMatchingInfoAdmin();
        }
		return null;
    }
	
}
