package com.dasom2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasom2.service.AdminService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/adminPage")
    public String adminPage(Model model, HttpSession session) {
    	
		if (session.getAttribute("userId") == "ksw") {
            return "adminPage";
        }
		return "mainPage";
    }
	
	@ResponseBody
    @PostMapping("/getUserInfoAdmin")
    public List<Map<String, Object>> getUserInfoAdmin(HttpSession session, @RequestParam(value = "userId", required = false) String userId) {
		
		if (session.getAttribute("userId").equals("aa")) {
	        if (userId == null || userId.isEmpty()) {
	            // userId가 주어지지 않은 경우 기본값 사용
	            return adminService.getUserInfoAdmin();
	        } else {
	            // userId가 주어진 경우 해당 값을 사용
	            return adminService.getUserInfoAdmin(userId);
	        }
	    }
	    return null;
    }
	
	@ResponseBody
    @PostMapping("/getScheduleInfoAdmin")
    public List<Map<String, Object>> getScheduleInfoAdmin(HttpSession session) {
		
		if (session.getAttribute("userId") == "ksw") {
            return adminService.getScheduleInfoAdmin();
        }
		return null;
    }
}
