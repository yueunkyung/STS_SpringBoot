package com.shinhan.sbproject;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.sbproject.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardTest {
	//주입하고 싶은게 하나일때 @Autowired 사용한다.
	@Autowired
	BoardRepository brepo;
	
	@Test
	void f14() {
		java.util.Date d = new java.util.Date(); 
		Timestamp date = new Timestamp(d.getTime()-1000000*60*60*12);
		Timestamp date2= new Timestamp(d.getTime()+1000000*60*60*12);
		System.out.println(date);
		System.out.println(date2);
		System.out.println(d.getTime());
		long bno1=1L;
		long bno2=100L;
		String writer="user3";
		String title="스프링";
		
		List<BoardVO> blist =brepo.findByRegDateBetweenAndBnoBetweenAndWriterNotLikeAndTitleContainingAndContentNotNullOrderByBno(date,date2,bno1, bno2, writer, title);
		blist.forEach(board->{
			log.info("title or content  조건조회:" + board.toString());
		});
	}

	//@Test
	void f13() {
		List<BoardVO> blist = brepo.findByContentContainingOrTitleContaining("월요일","월요일");
		blist.forEach(board -> {
			log.info("ContentContainingOrTitleContaining 조건조회: "+board.toString());
		});
	}

	//@Test
	void f12() {
		List<BoardVO> blist = brepo.findByWriterOrderByRegDateDesc("user3");
		blist.forEach(board -> {
			log.info("WriterOrderByRegDateDesc 조건조회: "+board.toString());
		});
	}
	
	//@Test
	void f11() {
		List<BoardVO> blist = brepo.findByBnoGreaterThanAndBnoLessThanEqual(40L, 45L);
		blist.forEach(board -> {
			log.info("GreaterThanAndBnoLessThanEqual 조건조회: "+board.toString());
		});
	}
	
	//@Test
	void f10() {
		List<BoardVO> blist = brepo.findByContentContaining("다");
		blist.forEach(board -> {
			log.info("Containing 조건조회: "+board.toString());
		});
	}
	
	//@Test
	void f9() {
		List<BoardVO> blist = brepo.findByContentLike("%다%");
		blist.forEach(board -> {
			log.info("Like 조건조회: "+board.toString());
		});
	}
	
	//@Test
	void f8() {
		List<BoardVO> blist = brepo.findByBnoGreaterThan(50L);
		blist.forEach(board -> {
			log.info("bno조건조회: "+board.toString());
		});
	}

	//@Test
	void f7() {
		List<BoardVO> blist = brepo.findByWriter("user3");
		List<BoardVO> blist2 = brepo.findByContent("재미있다");
		blist.forEach(board->{
			log.info("title 조건 조회: "+board.toString());
		});
		blist2.forEach(board->{
			log.info("content 조건 조회: "+board.toString());
		});
	}
	
	//@Test
	void f6() {
		log.info("board건 수: " + brepo.count());
	}
	
	//@Test
	void f5() {
		//객체 지우기
		Long searchId = 19L;
		brepo.findById(searchId).ifPresent(b->{
			brepo.delete(b);
		});
		//Id로 지우기
		brepo.deleteById(18L);
	}
	
	//@Test
	void f4() {
		Long searchId = 10L;
		brepo.findById(searchId).ifPresent(b->{
			b.setTitle("화요일....");
			b.setContent("오늘 점심 메뉴는? 국밥");
			b.setWriter("용수");
			BoardVO update_board = brepo.save(b);
			log.info("원본 : " + b);
			log.info("수정 : " + update_board);
		});
	}
	
	//@Test
	void f3() {
		Long searchId = 20L;
		brepo.findById(searchId).ifPresentOrElse(b->{
			log.info("조회한 정보:" + b);
		}, ()->{
			log.info("존재하지 않음");
		});
	}
	
	//@Test
	void f2() {
		brepo.findAll().forEach(board->{
			log.info(board.toString());
		});
	}
	
	//@Test
	void f1() {
		IntStream.rangeClosed(21, 40).forEach( i -> {
			BoardVO board = BoardVO.builder()
					.title("Java" + i)
					.content("기억난다....")
					.writer("user" + i%5)
					.build();
			BoardVO new_board = brepo.save(board);
			log.info("생성된 board : " + board);
			log.info("입력된 board : " + new_board);
			log.info(board.equals(new_board)?"내용 같음":"내용 다름");
		});
	}
}

/*
//주입하고 싶은게 여러개일때 @RequiredArgsConstructor 사용한다.
@Slf4j
@SpringBootTest
@RequiredArgsConstructor
public class BoardTest {
	
	//@Autowired
	final BoardRepository brepo;
	
	@Test
	void f1() {
		IntStream.rangeClosed(1, 20).forEach( i -> {
			BoardVO board = BoardVO.builder()
					.title("스프링부트" + i)
					.content("재미있다")
					.writer("user" + i%5)
					.build();
			brepo.save(board);
		});
	}
}
*/
