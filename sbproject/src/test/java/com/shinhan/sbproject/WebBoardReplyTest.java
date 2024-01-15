package com.shinhan.sbproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.webboard.WebBoard;
import com.shinhan.sbproject.webboard.WebBoardRepository;
import com.shinhan.sbproject.webboard.WebReply;
import com.shinhan.sbproject.webboard.WebReplyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WebBoardReplyTest {
	@Autowired
	WebBoardRepository boardRepo;
	
	@Autowired
	WebReplyRepository replyRepo;
	
	//@Test
	void f4() {
		//111, 112, 113 게시글의 댓글을 3개씩 입력
		List<Long> alist = Arrays.asList(111L, 112L, 113L);
		boardRepo.findAllById(alist).forEach(board -> {
			IntStream.rangeClosed(1, 3).forEach(i->{
				WebReply reply = WebReply.builder()
						.replyText("두둥 !! Test...." + i )
						.replyer("zzzin->"+ board.getBno())
						.board(board)	//어떤 게시글의 댓글인지 반드시 넣어야 한다.
						.build();
				replyRepo.save(reply);
			});
		});
	}
	
	//@Test
	void f3() {
		//105, 106, 107 게시글의 댓글을 3개씩 입력
		List<Long> alist = Arrays.asList(105L, 106L, 107L);
		boardRepo.findAllById(alist).forEach(board -> {
			List<WebReply> rlist = new ArrayList<>();
			
			IntStream.rangeClosed(1, 3).forEach(i->{
				WebReply reply = WebReply.builder()
						.replyText("알림...." + i )
						.replyer("친구->"+ board.getBno())
						.board(board)	//어떤 게시글의 댓글인지 반드시 넣어야 한다.
						.build();
				rlist.add(reply);
			});
			
			board.setReplies(rlist);
			boardRepo.save(board);
		});
	}
	
	//@Test
	void f2() {
		boardRepo.deleteAll();
		replyRepo.deleteAll();
	}
	
	//@Test
	void f1() {
		//Board 100개 insert
		//Reply 1, 10, 20 board 댓글
		
		IntStream.rangeClosed(1, 100).forEach(i->{
			WebBoard board = WebBoard.builder()
						.title("불금"+i)
						.content("열심히 놀아야지요~~" + i/10)
						.writer("user"+i%5)
						.build();
			
			List<WebReply> replies = new ArrayList<>();

			if(i==1 || i ==10 || i==20) {
				IntStream.rangeClosed(1, 5).forEach(j->{
					WebReply reply = WebReply.builder()
							.replyText("댓글...." + i +"--"+ j)
							.replyer("나그네" + j)
							.board(board)	//어떤 게시글의 댓글인지 반드시 넣어야 한다.
							.build();
					replies.add(reply);
				});
				
				board.setReplies(replies);
			}
			boardRepo.save(board);
		});
	}
}
