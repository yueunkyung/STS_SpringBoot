package com.shinhan.sbproject.webboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reply")
@Tag(name="댓글", description = "여기에서는 WebBoardReply CRUD 가능")
public class WebBoardReplyController {
	
	@Autowired
	WebReplyRepository replyRepo;
	
	@Autowired
	WebBoardRepository boardRepo;
	
	//조회
	@GetMapping("/list/{bno}")
	public List<WebReply> f1(@PathVariable("bno") Long bno) {
		WebBoard board = WebBoard.builder().bno(bno).title("aa").build();
		return replyRepo.findByBoard(board); 
	}
	
	//입력
	@PostMapping("/add/{bno}")
	public ResponseEntity<List<WebReply>> f2(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		WebBoard board = boardRepo.findById(bno).orElse(null);
		reply.setBoard(board);
		replyRepo.save(reply);
		
		//저장 후 댓글 리스트 불러오기
		//상태값과 data를 같이 넘기기
		return new ResponseEntity<>(replyRepo.findByBoard(board), HttpStatus.CREATED); 
	}
	
	//수정
	@PutMapping("/update/{bno}")
	public ResponseEntity<List<WebReply>> f3(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		replyRepo.findById(reply.getRno()).ifPresent(original->{
			original.setReplyText(reply.getReplyText());
			original.setReplyer(reply.getReplyer());
			replyRepo.save(original);
		});

		//저장 후 댓글 리스트 불러오기
		//상태값과 data를 같이 넘기기
		WebBoard board = WebBoard.builder().bno(bno).title("aa").build();
		return new ResponseEntity<>(replyRepo.findByBoard(board), HttpStatus.OK); 
	}
	
	//삭제
	@DeleteMapping("/delete/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> f4(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
		replyRepo.deleteById(rno);

		//저장 후 댓글 리스트 불러오기
		//상태값과 data를 같이 넘기기
		WebBoard board = WebBoard.builder().bno(bno).title("aa").build();
		return new ResponseEntity<>(replyRepo.findByBoard(board), HttpStatus.OK); 
	}
	
}
