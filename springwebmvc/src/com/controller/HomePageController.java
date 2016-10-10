package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HomePageController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String printHello(Model model) {
		
		model.addAttribute("message", "Hello Spring MVC Framework! : ");
		return "index";
	}
}
