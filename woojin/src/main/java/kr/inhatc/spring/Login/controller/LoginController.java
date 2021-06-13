package kr.inhatc.spring.Login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class LoginController {
	@GetMapping("/login/login")
	public void login() {
		log.info("로그인 진행중");
	}
	
	@GetMapping("/login/accessDenied")
	public void accessDenied() {
		log.info("접근금지");
	}
	@GetMapping("/login/logout")
	public void logout() {
		log.info("가다 뒤져");
	}
}
