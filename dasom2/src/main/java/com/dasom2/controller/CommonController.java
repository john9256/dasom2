package com.dasom2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dasom2.service.CommonService;

@Controller
public class CommonController {
	
	@Autowired 
	CommonService CommonService;
	
	// 기준 정보 get
	@GetMapping("/getCriteriaData")
	public List<String> getCriteriData(String criteria) {
		return CommonService.getCriteriaData(criteria);
    
	}
	
	
}
