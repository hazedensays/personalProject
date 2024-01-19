package com.cm.personalProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cm.personalProject.service.KakaoAPI;
import com.cm.personalProject.service.NaverAPI;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping(value = "/social")
@Controller
public class LoginController {
	
	private KakaoAPI kakao;
	private NaverAPI naver;
	
	@GetMapping("/loginPage")
	public void getLoginPage() {

	}
	
	@GetMapping("/klogin")
	public String kLogin(@RequestParam("code") String code) {
		System.out.println(code);
		String access_token = kakao.getAccessToken(code);
        System.out.println("controller access_token : " + access_token);
        
        String userInfo = kakao.getUserInfo(access_token);
        
        if ("success".equals(userInfo)) {
        	return "redirect:/home";
        } else {
        	return "redirect:/social/loginPage";
        }
	}
	
	@GetMapping("/nlogin")
	   public String nlogin(@RequestParam("code") String code) {
	      System.out.println(code);

	      String access_token = naver.getAccessToken(code);
	      
	      String userInfo = naver.getUserInfo(access_token);

	      if ("success".equals(userInfo)) {
	         return "redirect:/home";
	      } else {
	         return "redirect:/social/loginPage";
	      }

	   }
	
	
	
}
