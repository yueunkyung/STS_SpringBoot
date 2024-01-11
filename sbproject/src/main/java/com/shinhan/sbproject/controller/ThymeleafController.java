package com.shinhan.sbproject.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shinhan.sbproject.repository.FreeBoardRepository;
import com.shinhan.sbproject.vo3.FreeBoard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ThymeleafController {

	@Autowired
	FreeBoardRepository boardRepo;
	
	@GetMapping("/freeboard/list")
	public void f3(Model model) {
		model.addAttribute("loginUser","user1");
		model.addAttribute("myFriend","user2");
		model.addAttribute("blist", boardRepo.findAll());

		boardRepo.findById(20L).ifPresent(b->{
			log.info(b.toString());
		});
	}
	
	
	@GetMapping("/hello2")
	public String f2(Model model) {
		model.addAttribute("greeting", "안녕");
		FreeBoard board = FreeBoard.builder()
				.bno(99L)
				.title("글제목")
				.writer("작성자")
				.regdate(new Timestamp(System.currentTimeMillis()))
				.build();
		model.addAttribute("board", board);
		
		return "hello1";
	}
	
	@GetMapping("/hello1")
	public void f1(Model model) {
		log.info("hello1 요청");
		model.addAttribute("greeting", "감사합니다.");
		model.addAttribute("board", boardRepo.findById(6L).orElse(null));
		//접두사: classpath:templates
		//접미사: .html
		//return "templates/hello1.html"
	}
	
// ======================== 참고 ========================
// : 콜론 / ; 세미콜론 / , 콤마 / {}브레이스 / '' 싱글쿼트 / |수직바

}
