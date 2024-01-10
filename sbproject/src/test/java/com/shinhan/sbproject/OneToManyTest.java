package com.shinhan.sbproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.PDSBoardRepository;
import com.shinhan.sbproject.repository.PDSFileRepository;
import com.shinhan.sbproject.vo2.PDSBoard;
import com.shinhan.sbproject.vo2.PDSFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OneToManyTest {
	@Autowired
	PDSBoardRepository brepo;
	
	@Autowired
	PDSFileRepository frepo;

	//PPT 74p
	//3.Board별 File의 count얻기(File->Board)
	//@Test
	void getFileCount3() {
		frepo.getFilesCount().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
	}

	//2.Board별 File의 count얻기(File->Board) 불가 -> PDSFileRepository에 native 쿼리 작성
	//@Test
	void fileCount2() {
		frepo.getFileCountByBoard().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
	}
	
	//1.Board별 File의 count얻기(Board->File)
	//@Test
	void fileCount() {
		brepo.findAll().forEach(b->{
			log.info(b.getPname()+"===>>"+b.getFiles2().size());
		});
	}
	
	//Board를 통해서 File을 저장했다면 PDSFile 테이블의 pdsno pid로 들어간다.
	//File만 저장했다면 pdsno가 null이다.
	
	//@Test 
	void fileUpdate2() {
		Long pid = 2L;
		PDSBoard board = brepo.findById(pid).orElse(null);
		
		if(board == null) return;
		
		List<PDSFile> flist = board.getFiles2();
		
		frepo.findByPdsfilenameContaining("eye").forEach(f->{
			flist.add(f);
		});
		
		brepo.save(board);
	}
	
	//@Test 
	void fileUpdate() {
		//11번 첨부파일을 2번 board에 추가하고자 한다.
		Long fno = 11L;
		Long pid = 2L;
		frepo.findById(fno).ifPresent(f->{
			brepo.findById(pid).ifPresent(b->{
				b.getFiles2().add(f);
				brepo.save(b);
			});
		});
	}
	
	//@Test
	void fileSave() {
		IntStream.rangeClosed(1, 5).forEach(i->{
			PDSFile file = PDSFile.builder()
					.pdsfilename("eye-"+i)
					.build();
			frepo.save(file);
		});
	}
	
	//@Test
	void selectByBoard() {
		Long pid = 2L;
		
		brepo.findById(pid).ifPresent(board->{
			log.info("pname: " + board.getPname());
			log.info("pwriter: " + board.getPwriter());
			log.info("pFiles2: " + board.getFiles2());
			log.info("pFiles2 건 수: " + board.getFiles2().size());
			log.info("*****************************************");
		});
	}
	
	//@Test
	void fileSelect() {
		frepo.findAll().forEach(f->log.info(f.toString()));
	}

	//@Test
	void boardSelect() {
		brepo.findAll().forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void insert() {
		//Board(1), file(n)
		List<PDSFile> flist = new ArrayList<>();
		IntStream.rangeClosed(1, 5).forEach(i->{
			PDSFile file = PDSFile.builder()
					.pdsfilename("face-"+i)
					.build();
			flist.add(file);
		});
		
		PDSBoard board = PDSBoard.builder()
				.pname("눈이옵니다.")
				.pwriter("지현")
				.files2(flist)
				.build();
		brepo.save(board);
	}
}
