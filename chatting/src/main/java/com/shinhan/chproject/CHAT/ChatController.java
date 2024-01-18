package com.shinhan.chproject.CHAT;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChatController {

	@GetMapping("/chat")
	public String chat() {
		return "chatting";
	}
}
