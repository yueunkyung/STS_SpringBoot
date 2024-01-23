package com.shinhan.sbproject.webboard;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;
import com.shinhan.sbproject.security.MemberService;
import com.shinhan.sbproject.vo.MemberDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // 요청을 보내면 실행, data를 Return : @RestController = @Controller + @ResponseData
@RequiredArgsConstructor
@RequestMapping("/rest/webboard")
//@CrossOrigin(origins = "http://localhost:3000") // 동의 정책에 어긋나도 허용해주겠다.
public class WebBoardControllerRest {
	final WebBoardRepository boardRepo;
	final MemberService mservice;

	// @CrossOrigin(origins = "http://localhost:3000") //사용하려는 함수에만 적용할 수 있다.
	@GetMapping("/list.do")
	public List<WebBoard> f1(@ModelAttribute("pageVO") PageVO page) { // @ModelAttribute 파라미터에 들어온 값을 바로 값을 넘기는 어노테이션 ==
																		// model.addAttribute
		page.setSize(10);
		Predicate predicate = boardRepo.makePredicate(page.getType(), page.getKeyword());

		Pageable paging = page.makePageable(0, "bno"); // 0-> direction desc

		Page<WebBoard> result = boardRepo.findAll(predicate, paging);

		int totalPage = result.getTotalPages();
		int minPage = Math.min(5, totalPage);

		PageMarker<WebBoard> pageMaker = new PageMarker<>(result, minPage, page.getSize());

		// paging, predicate, sort 추가함
		// page.setType("writer"); -> 페이지에서 option 미리 set
		// page.setType("title"); -> 페이지에서 option 미리 set
		return result.getContent();
	}

	@GetMapping("/detail.do/{bno}")
	public WebBoard f2(@PathVariable("bno") Long bno, PageVO page) {
		return boardRepo.findById(bno).orElse(null);
	}

	@PutMapping("/update.do")
	
	public Integer f3(@RequestBody WebBoard board, PageVO page) {
		log.info(board.toString());
		WebBoard updateBoard = boardRepo.save(board);

		return updateBoard == null ? -1 : 0;
	}

//	@GetMapping("/insert.do")
//	public void f4() {
//
//	}

	@PostMapping("/insert.do")
	public Integer f5(@RequestBody WebBoard board) {
		log.info(board.toString());
		WebBoard newboard = boardRepo.save(board);
		
		return newboard.getBno()!=null?0:-1;
	}

	// 삭제추가하기
	@DeleteMapping("/delete.do/{bno}")
	public String f6(@PathVariable("bno") Long bno) {
		boardRepo.deleteById(bno);
		return "삭제성공";
	}
}
