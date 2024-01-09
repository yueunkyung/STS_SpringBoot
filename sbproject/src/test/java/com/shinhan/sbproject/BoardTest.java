package com.shinhan.sbproject;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.sbproject.vo.BoardVO;
import com.shinhan.sbproject.vo.QBoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardTest {
	//주입하고 싶은게 하나일때 @Autowired 사용한다.
	@Autowired
	BoardRepository brepo;

	
	//@Test
	void f20() {
		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		Long bno = 5L;
		String writer = "user3";
		String content = "%억난%";
		
		if(bno != null) builder.and(board.bno.gt(bno));
		if(writer != null) builder.or(board.writer.eq(writer));
		if(content != null) builder.and(board.content.like(content));
		
		log.info(builder.toString());

		//boardVO.bno > 5 || boardVO.writer = user3 && boardVO.content like %억난%
		// ==> or 와 and 가 있으면 and 먼저 실행함. => boardVO.bno > 5 || (boardVO.writer = user3 && boardVO.content like %억난%)
		
		//동적 SQL 만들기
		List<BoardVO> blist = (List<BoardVO>)brepo.findAll(builder);
		blist.forEach(b->{
			log.info(b.toString());
		});
	}
	
	//@Test
	void f19() {
		brepo.selectByWriter("user3").forEach(sarr -> {
			 log.info("title: "+ sarr[0]);
			 log.info("content: "+ sarr[1]);
			 log.info("writer: "+ sarr[2]);
			 log.info("bno: "+ sarr[3]);
			 log.info("regDate: "+ sarr[4]);
			 log.info("============================================");
		});

	}
	
	//@Test
	void f18() {
		//List<BoardVO> blist = brepo.selectByTitleAndWriter5(5L, "Java", "user");
		//blist.forEach(b->log.info(b.toString()));

		List<Object[]> blist = brepo.selectByTitleAndWriter6(5L, "Java", "user");
		blist.forEach(arr->log.info(Arrays.toString(arr)));
	}
	
	//@Test
	void f17() {
		//Pageable paging = PageRequest.of(1, 5);
		//Pageable paging = PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC, "writer","title"));
		//조건에 맞는 Data가 22건이면, 한페이지가 5건, 전체페이지는 5페이지, 마지막페이지는 (0~4)는 2건임!
		Pageable paging = PageRequest.of(4, 5, Sort.by("writer").ascending());
		//Page<BoardVO> result = brepo.findAll(paging);
		Page<BoardVO> result = brepo.findByBnoBetween(1L, 100L, paging);
		
		log.info("페이지사이즈 getSize: "+result.getSize());
		log.info("현재페이지 getNumber: "+result.getNumber());
		log.info("getNumberOfElements: "+result.getNumberOfElements());
		log.info("건수 getTotalElements: "+result.getTotalElements());
		log.info("페이지건수 getTotalPages: "+result.getTotalPages());
		log.info("내용 getContent: "+result.getContent());
		log.info("getPageable: "+result.getPageable());
		log.info("getSort: "+result.getSort());
		
		result.getContent().forEach(b -> log.info(b.toString()));
	}
	
	//@Test
	void f16() {
		Pageable paging = PageRequest.of(0, 6); //page(몇페이지), pagesize(한페이지 건수)
		//where bno>5 ..... 6부터 나온다.(0페이지에 6건: 1~11)
		//                            (1페이지에 6건: 12~17)  
		brepo.findByBnoGreaterThan(5L, paging).forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void f15() {
		String writer = "user3";
		int cnt = brepo.countByWriter(writer);
		log.info("user3이 작성한 board 건 수" + cnt);
		
		brepo.findByWriter(writer).forEach(b->log.info(b.toString()));
	}
	
	//@Test
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
