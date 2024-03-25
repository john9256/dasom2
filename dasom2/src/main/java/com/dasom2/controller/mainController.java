package com.dasom2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasom2.mapper.MainMapper;
import com.dasom2.service.LoginService;
import com.dasom2.service.MainService;
import com.dasom2.vo.MeetingScheduleVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class mainController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MainService MainService;
	
	//메인페이지
    @GetMapping("/mainPage")
    public String mainPage(Model model, HttpSession session) {
        // 로그인이 필요한 경우 로그인 페이지로 리디렉션
        if (session.getAttribute("userId") == null) {
            return "redirect:/loginPage";
        }

        model.addAttribute("userId", session.getAttribute("userId"));
        return "mainPage";
    }
    
    // 소개팅 스케줄 날짜 선택 모달 data
    @ResponseBody
    @PostMapping("/getMeetingSchedule")
    public List<MeetingScheduleVO> getMeetingSchedule(@RequestParam("userId") String userId) {
    	return MainService.getMeetingSchedule(userId);
    }
    
    @ResponseBody
    @PostMapping("/updateScheduleSelection")
    public void updateScheduleSelection(@RequestParam("userId") String userId, @RequestParam("episode") String episode, @RequestParam("episodeSelected") Boolean episodeSelected) {
    	MainService.updateScheduleSelection(userId, episode, episodeSelected);
    }
    
}
