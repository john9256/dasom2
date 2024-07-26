package com.dasom2.controller;

import java.util.HashMap;
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
	
	@Autowired
	CommonMapper CommonMapper;
	
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
    	model.addAttribute("accessToken", session.getAttribute("accessToken"));
    	model.addAttribute("userName", MainService.getUserNameByUserId(String.valueOf(session.getAttribute("userId"))));
    	
        if (session.getAttribute("userId") == null) {
            return "redirect:/login/kakao";
        }
        
//        if(MainService.getPassFlagbyUser(String.valueOf(session.getAttribute("userId")))) 
        if(MainService.checkUserInfoExist(String.valueOf(session.getAttribute("userId")))) 
        {
        	return "mainPage";
        }
        else {
//        	return "mainPage";
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
    
    // 매칭 인원 선택 모달
    @ResponseBody
    @PostMapping("/getMatchingInfo")
    public List<Map<String, Object>> getMatchingInfo(@RequestParam("userId") String userId, HttpSession session) {
    	if(userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
    		
    		return MainService.getMatchingInfo(userId);
    	}
			return null;
    }
    
 // 소개팅 현황 모달
    @ResponseBody
    @PostMapping("/getParticipantList")
    public List<Map<String, Object>> getParticipantList(@RequestParam("userId") String userId, HttpSession session) {
    	if(userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
    		
    		return MainService.getParticipantList(userId);
    	}
			return null;
    }
    
    // 날짜 선택
    @ResponseBody
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/ScheduleSelectionInsert")
    public Map<String, Object> ScheduleSelection(@RequestParam("userId") String userId, @RequestParam("episode") String episode, @RequestParam("episodeSelected") Boolean episodeSelected, HttpSession session) {
    	Map<String, Object> status = new HashMap<String, Object>();
    	
		try {
			if (userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
				if (episodeSelected == true) {
					return MainService.insertParticipantUser(userId, episode, episodeSelected);
				} else {
					return MainService.deleteParticipantUser(userId, episode, episodeSelected);
				}
			}
			status.put("status", "hack");
			return status;
		} catch (Exception e) {
			throw e;
		}
    }
    
    // 중복된 이성이 있는대도 신청하는 경우 날짜 선택
    @ResponseBody
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/ScheduleSelectionDuplicateInsert")
    public Map<String, Object> ScheduleSelectionDuplicate(@RequestParam("userId") String userId, @RequestParam("episode") String episode, @RequestParam("episodeSelected") Boolean episodeSelected, HttpSession session) {
		Map<String, Object> status = new HashMap<String, Object>();

		try {
			if (userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
				return MainService.insertParticipantUserDupliacate(userId, episode, episodeSelected);
			}
			status.put("status", "hack");
			return status;
		} catch (Exception e) {
			throw e;
		}
    	
    }
    
    // 매치 이성 선택
    @ResponseBody
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/matchSelection")
    public Map<String, Object> matchSelectionInsert(@RequestParam("userId") String userId, @RequestParam("episode") String episode, @RequestParam("nickName") String nickName, @RequestParam("nickNameSelected") Boolean nickNameSelected, HttpSession session) {
    	Map<String, Object> status = new HashMap<String, Object>();
    	
    	try {
	    	if(userId.equalsIgnoreCase(session.getAttribute("userId").toString())) {
		    	if(nickNameSelected == true) {
		    		return MainService.insertMatchPick(userId, episode, nickName);
		    	}
		    	else {
		    		return MainService.deleteMatchPick(userId, episode, nickName);
		    	}
	    	}
	    	status.put("status", "hack");
			return status;
    	}
    	catch (Exception e) {
    		throw e;
		}
		
    }
    
    
    
}
