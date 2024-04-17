package com.dasom2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasom2.service.LoginService;
import com.dasom2.service.MainService;
import com.dasom2.service.testService;
import com.dasom2.vo.MeetingScheduleVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class mainController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MainService MainService;
	
	@Autowired
	testService testService;
	
	@GetMapping("/test1")
	public String test1() {
		return "index";
	}
	
	//메인페이지
    @SuppressWarnings("unused")
	@GetMapping("/mainPage")
    public String mainPage(Model model, HttpSession session) {
        // 로그인이 필요한 경우 로그인 페이지로 리디렉션
    	
    	model.addAttribute("userId", session.getAttribute("userId"));
    	
        if (session.getAttribute("userId") == null) {
            return "redirect:/loginPage";
        }
        
        // 소개팅 신청 서류 합격 인원에게만 메인 페이지 보여주기
        if(MainService.getPassFlagbyUser(String.valueOf(session.getAttribute("userId")))) 
        {
        	return "mainPage";
        }
        else {
        	return "waitingPage";
        }
    }
    
    // 소개팅 스케줄 날짜 선택 모달 data
    @ResponseBody
    @PostMapping("/getMeetingSchedule")
    public List<MeetingScheduleVO> getMeetingSchedule(@RequestParam("userId") String userId, HttpSession session) {
    	if(userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
    	return MainService.getMeetingSchedule(userId);
    	}
		return null;
    }
    
    @ResponseBody
    @PostMapping("/ScheduleSelection")
    public Map<String, Object> ScheduleSelection(@RequestParam("userId") String userId, @RequestParam("episode") String episode, @RequestParam("episodeSelected") Boolean episodeSelected, HttpSession session) {
    	if(userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
    	 if(episodeSelected == true) {
    		 return MainService.insertParticipantUser(userId, episode, episodeSelected); 
    	 }
    	 else {
    		 return MainService.deleteParticipantUser(userId, episode, episodeSelected);
    	 }
    	}
    	Map<String, Object> status = new HashMap<String, Object>();
		status.put("status", "fail");
		return status;
    }
    
    @ResponseBody
    @PostMapping("/getMatchingInfo")
    public List<Map<String, Object>> getMatchingInfo(@RequestParam("userId") String userId, HttpSession session) {
    	if(userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
    		
    		return MainService.getMatchingInfo(userId);
    	}
			return null;
    }
    
}
