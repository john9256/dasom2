package com.dasom2.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
    LoginLogService loginLogService;
  
    //로그인 페이지 이동
    @GetMapping("/loginPage")
    public String loginPage(Model model, HttpSession session) {
    	
    	if (session != null && !session.isNew()) {
            session.invalidate(); // 세션 초기화
        }
    	
        return "loginPage";
    	
    }
    
    //로그인 실행
    @PostMapping("/loginDo")
    public String loginDo(String userId, String password, Model model, HttpSession session, HttpServletRequest request) {
    	
    	/* 사용자 확인 */
        String userCheck = loginService.validateUser(userId, password);
        
        LoginLogVO log = new LoginLogVO();
        String currentIp = request.getRemoteAddr();
        log.setUserId(userId);
        log.setIp(currentIp);

        Integer latestCountByIp = loginLogService.getLatestCountByIp(currentIp);

        if(latestCountByIp != null && latestCountByIp >= 5) {
            return "contactAdmin";
        }

        if(userCheck == null) {
            log.setSuccessflag(false);
            log.setCount(latestCountByIp == null ? 1 : latestCountByIp + 1);
            loginLogService.insertLog(log);
            return "loginFail";
        }
        
        log.setSuccessflag(true);
        log.setCount(0); // 로그인 카운트 0으로 초기화
        loginLogService.insertLog(log);
         
    	
		/* 세션 값 저장 */
    	session.setAttribute("userId", userId);
    	
    	model.addAttribute("userId", userId);
    	model.addAttribute("surveyCheck", loginService.surveyCheck(userId));
        return "mainPage";
    }
    
    //메인페이지
    @GetMapping("/mainPage")
    public String mainPage(Model model, HttpSession session) {
        // 로그인이 필요한 경우 로그인 페이지로 리디렉션
        if (session.getAttribute("userId") == null) {
            return "redirect:/loginPage";
        }

        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("surveyCheck", loginService.surveyCheck(session.getAttribute("userId").toString()));

        return "mainPage";
    }
    
    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Remove all session attributes
        return "loginPage";
    }
    
}
