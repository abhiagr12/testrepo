package com.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginPageController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Principal principal) {
		if(principal!=null){
			return "redirect:/";
		}
		return "login";
	}
}
