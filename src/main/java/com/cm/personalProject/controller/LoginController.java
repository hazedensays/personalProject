package com.cm.personalProject.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cm.personalProject.entity.User;
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
	public String klogin(@RequestParam("code") String code) {
		System.out.println(code);
		String access_token = kakao.getAccessToken(code);
		System.out.println("controller access_token : " + access_token);

		String userInfo;
		try {
			userInfo = kakao.getUserInfo(access_token);
			if ("성공".equals(userInfo)) {
				return "redirect:/home";
			} else {
				return "redirect:/social/loginPage";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/social/loginPage";
		}

	}

	@GetMapping("/nlogin")
	public String nlogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println(code);

		String access_token = naver.getAccessToken(code);

		try {
			User userInfo = naver.getUserInfo(access_token).orElse(null);
			System.out.println("*****************" + userInfo);

			if (userInfo != null) {
				session.setAttribute("loginUser", userInfo);
				return "redirect:/home";
			} else {
				return "redirect:/social/loginPage";
			}

		} catch (Exception e) {

			return "redirect:/social/loginPage";
		}

	}

	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:/home";
	}
}
