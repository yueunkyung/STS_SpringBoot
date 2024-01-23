package com.shinhan.sbproject.webboard;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;
import com.shinhan.sbproject.security.MemberService;
import com.shinhan.sbproject.vo.MemberDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller //요청을 보내면 실행, page를 return
@RequiredArgsConstructor
@RequestMapping("/webboard")
public class WebBoardController {
	final WebBoardRepository boardRepo;
	final MemberService mservice;
	
	@GetMapping("/list.do")
	public void f1( Principal principal, Authentication auth, HttpSession session
			, Model model, @ModelAttribute("pageVO") PageVO page) { // @ModelAttribute 파라미터에 들어온 값을 바로 값을 넘기는 어노테이션 == model.addAttribute
		
		//로그인한 멤버의 정보를 알아내기
		//1.Principal 정보
		log.info("방법1 principal >>>>" + principal.toString());
		
		//2.Authentication 이용
		log.info("방법2 Authentication >>>>" + auth.getPrincipal());
		
		//3.scurityContextHolder를 이용
		log.info("방법3 SecurityContextHolder >>>>" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		//4.getName를 이용
		String mid = principal.getName();
		UserDetails user = mservice.loadUserByUsername(mid);
		log.info("방법4 로그인한 사람(security) >>>>" + user);
		
		//5.session를 이용
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		log.info("방법5 로그인한 사람(우리DB) >>>>" + member);
		//model.addAttribute("user", member); //page에서 사용하기 위함
		
		page.setSize(10);
		Predicate predicate = boardRepo.makePredicate(page.getType(), page.getKeyword());
		
		Pageable paging = page.makePageable(0, "bno"); // 0-> direction desc
		
		Page<WebBoard> result = boardRepo.findAll(predicate, paging);
		
		int totalPage = result.getTotalPages();
		int minPage = Math.min(5, totalPage);
		
		PageMarker<WebBoard> pageMaker = new PageMarker<>(result, minPage, page.getSize());
		
		model.addAttribute("blist", pageMaker);
		//paging, predicate, sort 추가함
		//page.setType("writer"); -> 페이지에서 option 미리 set 
		//page.setType("title"); -> 페이지에서 option 미리 set
	}

	@GetMapping("/detail.do")
	public void f2(Model model, Long bno, @ModelAttribute("pageVO") PageVO page
				, HttpSession session) {
		//model.addAttribute("board", boardRepo.findById(bno).orElse(null)); //orElse(null) or get() 둘 중 하나 해도 됨.
		model.addAttribute("board", boardRepo.findById(bno).get());
		
		/*
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		log.info("방법5 로그인한 사람(우리DB) >>>>" + member);
		model.addAttribute("user", member); //page에서 사용하기 위함
		*/
	}
	
	@PostMapping("/update.do")
	public String f3(WebBoard board, RedirectAttributes attr, PageVO page) {
		//model.addAttribute("board", boardRepo.findById(bno).orElse(null)); //orElse(null) or get() 둘 중 하나 해도 됨.
		//model.addAttribute("board", boardRepo.findById(bno).get());
		log.info(board.toString());
		boardRepo.save(board);
		attr.addFlashAttribute("message", "수정성공");
		attr.addAttribute("page", page.getPage());
		attr.addAttribute("size", page.getSize());
		attr.addAttribute("keyword", page.getKeyword());
		attr.addAttribute("type", page.getType());
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
