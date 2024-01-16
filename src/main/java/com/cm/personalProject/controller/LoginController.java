package com.cm.personalProject.controller;

import java.util.Map;

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
		System.out.println(code);
		String access_token = kakao.getAccessToken(code);
        System.out.println("controller access_token : " + access_token);
        
        Map<String, Object> userInfo = kakao.getUserInfo(access_token);
        //System.out.println(userInfo.get("username"));
        //System.out.println(userInfo.get("useremail"));
        //System.out.println(userInfo.get("userphone"));
        System.out.println("nickname : " + userInfo.get("nickname"));
	    return "home";
	}
	
	
	
}
