package com.shinhan.sbproject.webboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/webboard")
public class WebBoardController {
	final WebBoardRepository boardRepo;
	
	@GetMapping("/list.do")
	public void f1(Model model, HttpServletRequest request) {
		model.addAttribute("blist", boardRepo.findAll());
		
	}

	@GetMapping("/detail.do")
	public void f2(Model model, Long bno) {
		//model.addAttribute("board", boardRepo.findById(bno).orElse(null)); //orElse(null) or get() 둘 중 하나 해도 됨.
		model.addAttribute("board", boardRepo.findById(bno).get());
	}
	
	@PostMapping("/update.do")
	public String f3(WebBoard board, RedirectAttributes attr) {
		//model.addAttribute("board", boardRepo.findById(bno).orElse(null)); //orElse(null) or get() 둘 중 하나 해도 됨.
		//model.addAttribute("board", boardRepo.findById(bno).get());
		log.info(board.toString());
		boardRepo.save(board);
		attr.addFlashAttribute("message", "수정성공");
		return "redirect:list.do";
	}
	
	@GetMapping("/insert.do")
	public void f4() {
		
	}
	
	@PostMapping("/insert.do")
	public String f5(WebBoard board, RedirectAttributes attr) {
		log.info(board.toString());
		WebBoard newboard = boardRepo.save(board);
		attr.addFlashAttribute("message", newboard!=null?"입력성공":"입력실패");
		return "redirect:list.do";
	}
	
	//삭제추가하기
	@GetMapping("/delete.do")
	public String f6(Long bno, RedirectAttributes attr) {
		boardRepo.deleteById(bno);
		attr.addFlashAttribute("message", "삭제성공");
		return "redirect:list.do";
	}
}
