package com.cm.personalProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cm.personalProject.service.KakaoAPI;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping(value = "/social")
@Controller
public class LoginController {
	
	private KakaoAPI kakao;
	
	@GetMapping("/loginPage")
	public void getLoginPage() {

	}
	
	@GetMapping("/login")
	public String login(@RequestParam("code") String code) {
		String access_Token = kakao.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
        
        return "home";
	}
	
	
}
