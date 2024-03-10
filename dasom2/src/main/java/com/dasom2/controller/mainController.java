package com.dasom2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dasom2.service.LoginLogService;
import com.dasom2.service.LoginService;
import com.dasom2.vo.LoginLogVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class mainController {
	
	@Autowired
	LoginService loginService;
	
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
    
    
}
