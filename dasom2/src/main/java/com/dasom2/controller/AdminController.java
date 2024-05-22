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
    	
    }
	
	@ResponseBody
    @PostMapping("/getUserInfo")
    public List<Map<String, Object>> getUserInfo(HttpSession session) {
		
		if (session.getAttribute("userId") == "ksw") {
            return ";
        }
    }
	
	@ResponseBody
    @PostMapping("/getScheduleInfo")
    public List<Map<String, Object>> getScheduleInfo(@RequestParam("userId") String userId, HttpSession session) {
		
		if (session.getAttribute("userId") == "ksw") {
            return "adminPage";
        }
    }
	
	@ResponseBody
    @PostMapping("/setPassFlag")
    public List<Map<String, Object>> setPassFlag(@RequestParam("userId") String userId, HttpSession session) {
		
		if (session.getAttribute("userId") == "ksw") {
            return "adminPage";
        }
    }
	
}
