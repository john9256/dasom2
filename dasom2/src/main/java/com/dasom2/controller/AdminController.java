package com.dasom2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasom2.service.AdminService;
import com.dasom2.service.CommonService;
import com.dasom2.service.MainService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	MainService MainService;
	
	@Autowired
	CommonService commonService;
	
	// 관리자 화면
	@GetMapping("/adminPage")
    public String adminPage(Model model, HttpSession session) {
		
		if (session.getAttribute("userId") == null) {
            return "redirect:/login/kakao";
        }
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
            return "adminPage";
        }
		return "mainPage";
    }
	
	// 유저 정보 get
	@ResponseBody
    @PostMapping("/getUserInfoAdmin")
    public List<Map<String, Object>> getUserInfoAdmin(HttpSession session) {
		
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
	            return adminService.getUserInfoAdmin();
	    }
	    return null;
    }
	
	// 스케줄 정보 get
	@ResponseBody
    @PostMapping("/getScheduleInfoAdmin")
    public List<Map<String, Object>> getScheduleInfoAdmin(HttpSession session) {
		
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
            return adminService.getScheduleInfoAdmin();
        }
		return null;
    }
	
	// 스케줄별 매칭 정보 get
	@ResponseBody
    @PostMapping("/getMatchingInfoAdmin")
    public List<Map<String, Object>> getMatchingInfoAdmin(HttpSession session) {
		
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
            return adminService.getMatchingInfoAdmin();
        }
		return null;
    }
	
	// 단순 스케줄 get
		@ResponseBody
	    @PostMapping("/getScheduleAdmin")
	    public List<Map<String, Object>> getScheduleAdmin(HttpSession session) {
			
			if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
	            return adminService.getScheduleAdmin();
	        }
			return null;
	    }
	
	// chance 1회 부여
	@ResponseBody
	@Transactional(rollbackFor = {Exception.class})
    @PostMapping("/increaseChance")
    public Map<String, Object> increaseChance(@RequestParam("userId") String userId, HttpSession session) {
		Map<String, Object> status = new HashMap<String, Object>();
		
		try {
		
			if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
				commonService.stackAdminHistory(session.getAttribute("userId").toString(), userId, "increaseChance");
	            return adminService.increaseChance(userId);
	        }
			status.put("status", "not admin");
			return status;
			
		}
		catch (Exception e) {
			throw e;
		}
		
    }
	
	// chance 1회 감소
	@ResponseBody
	@Transactional(rollbackFor = {Exception.class})
    @PostMapping("/decreaseChance")
    public Map<String, Object> decreaseChance(@RequestParam("userId") String userId, HttpSession session) {
		Map<String, Object> status = new HashMap<String, Object>();
		
		try {
			
			if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
				commonService.stackAdminHistory(session.getAttribute("userId").toString(), userId, "decreaseChance");
				return adminService.decreaseChance(userId);
	        }
			status.put("status", "not admin");
			return status;
			
		}
		catch (Exception e) {
			throw e;
		}
		
    }
	
	// passFlag 값 변경
	@ResponseBody
    @PostMapping("/changePassFlag")
    public Map<String, Object> changePassFlag(@RequestParam("userId") String userId, @RequestParam("passFlag") String passFlag, HttpSession session) {
		Map<String, Object> status = new HashMap<String, Object>();
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
            return adminService.changePassFlag(userId, passFlag);
        }
		status.put("status", "fail");
		return status;
    }
	
	// 스케줄 삭제
	@ResponseBody
	@Transactional(rollbackFor = {Exception.class})
    @PostMapping("/deleteSchedule")
    public Map<String, Object> deleteSchedule(@RequestParam("episode") String episode, HttpSession session) {
		Map<String, Object> status = new HashMap<String, Object>();
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
			if(commonService.stackAdminHistory(session.getAttribute("userId").toString(), episode, "deleteSchedule") == 1) {
				return adminService.deleteSchedule(episode);
			}
        }
		status.put("status", "fail");
		return status;
    }
	
	// 스케줄 추가
	@ResponseBody
	@Transactional(rollbackFor = {Exception.class})
    @PostMapping("/addSchedule")
    public Map<String, Object> addSchedule(String episode, int headCount, String location, HttpSession session) {
		Map<String, Object> status = new HashMap<String, Object>();
		if (adminService.checkAdmin(session.getAttribute("userId").toString())) {
			if(commonService.stackAdminHistory(session.getAttribute("userId").toString(), episode, "addSchedule") == 1) {
				return adminService.addSchedule(episode, headCount, location);
			}
        }
		status.put("status", "fail");
		return status;
    }
	
}
