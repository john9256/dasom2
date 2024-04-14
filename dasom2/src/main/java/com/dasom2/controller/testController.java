package com.dasom2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dasom2.service.testService;



@Controller
public class testController {
	
	@Autowired 
	testService testService;
	
	
}
