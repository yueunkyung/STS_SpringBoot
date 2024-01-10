package com.shinhan.sbproject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.shinhan.sbproject.repository.FreeBoardReplyRepository;
import com.shinhan.sbproject.repository.FreeBoardRepository;
import com.shinhan.sbproject.vo3.FreeBoard;
import com.shinhan.sbproject.vo3.FreeBoardReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TwoWayTest {

	@Autowired
	FreeBoardRepository boardRepo;

	@Autowired
	FreeBoardReplyRepository replyRepo;

	//@Test
	void f3() {
		Sort sort = Sort.by(Direction.ASC, "bno");
		Pageable page = PageRequest.of(1, 3, sort);
		Page<FreeBoard> result = boardRepo.findByWriter("user4", page);
		
		log.info("페이지수 : " + result.getTotalPages());
		log.info("건수 : " + result.getTotalElements());
		
		List<FreeBoard> blist = result.getContent();
		
		for(FreeBoard board:blist) {
			log.info("Bno >>>" + board.getBno());
			log.info("Title >>>" + board.getTitle());
			log.info("Writer >>>" + board.getWriter());
			log.info("Content >>>" + board.getContent());
			log.info("Replies 갯수 >>>" + board.getReplies().size());
			log.info("===============================================================");
		}
	}
	
	//@Test
	void f2() {
		Sort sort = Sort.by(Direction.ASC, "bno");
		Pageable page = PageRequest.of(1, 3, sort);
		List<FreeBoard> blist = boardRepo.findByBnoBetween(10L, 20L, page);
		for(FreeBoard board:blist) {
			log.info("Bno >>>" + board.getBno());
			log.info("Title >>>" + board.getTitle());
			log.info("Writer >>>" + board.getWriter());
			log.info("Content >>>" + board.getContent());
			log.info("Replies 갯수 >>>" + board.getReplies().size());
			log.info("===============================================================");
		}
	}
	
	//@Test
	void f1() {
		Sort sort = Sort.by(Direction.ASC, "bno");
		Pageable page = PageRequest.of(1, 3, sort);
		List<FreeBoard> blist = boardRepo.findByBnoGreaterThan(10L, page);
		for(FreeBoard board:blist) {
			log.info("Bno >>>" + board.getBno());
			log.info("Title >>>" + board.getTitle());
			log.info("Writer >>>" + board.getWriter());
			log.info("Content >>>" + board.getContent());
			log.info("Replies 갯수 >>>" + board.getReplies().size());
			log.info("===============================================================");
		}
	}
	
	//특정 board의 댓글 가져오기
	//@Test
	void replySelectByBoard() {
		FreeBoard board = FreeBoard.builder()
				.bno(20L)
				.title("AA")
				.build();
		List<FreeBoardReply> replylist = replyRepo.findByBoard2(board);
		replylist.forEach(reply->{
			log.info("rno >>> " + reply.getRno());
			log.info("reply >>> " + reply.getReply());
			log.info("replyer >>> " + reply.getReplyer());
			log.info("regdate >>> " + reply.getRegdate());
			log.info("updatedate >>> " + reply.getUpdatedate());
			log.info("===============================================================");
		});
	}
	
	//모든 댓글 가져오기
	//@Test
	void replySelect() {
		replyRepo.findAll(Sort.by(Direction.DESC, "rno")).forEach(reply->{
			log.info("내용 >>>>> " + reply.getReply());
			log.info("작성자 >>>>> " + reply.getReplyer());
			log.info("board 내용 >>>>> " + reply.getBoard2().getContent());
		});;
	}
	
	//board의 댓글의 갯수 출력
	//@Transactional //지연로딩일때 연관관계 table fetch하려면 반드시 추가한다. //FreeBoard가 fetch = FetchType.LAZY일때 사용함.
	//@Test
	void getReplyCount() {
		boardRepo.findAll().forEach(board->{
			log.info(board.getBno() + " ==> " + board.getReplies().size());
		});
	}
	
	//특정 board 댓글 입력 : 5L, 10L, 15L
	//@Test
	void replyInsert2() {
		List<Long> blist = Arrays.asList(5L, 10L, 15L);
		boardRepo.findAllById(blist).forEach(board -> {
			IntStream.range(1, 6).forEach(i->{
				FreeBoardReply reply = FreeBoardReply.builder()
						.reply("퍼스트존...."+board.getBno())
						.replyer("작성자"+i)
						.board2(board)
						.build();
				replyRepo.save(reply);
			});
		});
	}
		
	//특정 board 댓글 입력 : 20L
	//@Test
	void replyInsert() {
		FreeBoard board = boardRepo.findById(20L).orElse(null);
		IntStream.range(1, 6).forEach(i->{
			FreeBoardReply reply = FreeBoardReply.builder()
					.reply("나도나도...")
					.replyer("작성자"+i)
					.board2(board)
					.build();
			replyRepo.save(reply);
		});
	}
	
	//@Test
	void boardSelect() {
		boardRepo.findAll(Sort.by(Direction.DESC, "bno")).forEach(b->{
			log.info(b.toString());
		});
	} 
	
	//@Test
	void boardInsert() {
		//20건의 board 작성
		IntStream.range(1, 21).forEach(i->{
			FreeBoard board = FreeBoard.builder()
							.title("양방향연습" + i)
							.writer("user"+i%5)
							.content("아~카스처럼...맛있어")
							.build();
			boardRepo.save(board);
		});
	}
}
