package com.shinhan.sbproject;

import java.util.ArrayList;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;

import com.shinhan.sbproject.repository.PDSBoardRepository;
import com.shinhan.sbproject.repository.PDSFileRepository;
import com.shinhan.sbproject.vo2.PDSBoard;
import com.shinhan.sbproject.vo2.PDSFile;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Commit
@Slf4j
@SpringBootTest
public class OneToManyTest {
	@Autowired
	PDSBoardRepository brepo;
	
	@Autowired
	PDSFileRepository frepo;

	//PPT 79P
	@Test
	void fileSelectAll() {
		//frepo.findAll(Sort.by(Direction.DESC, "fno")).forEach(f->log.info(f.toString()));
		log.info("----------------------------------------");
		Pageable paging = PageRequest.of(0, 3);
		Page<PDSFile> result = frepo.findAll(paging);
		
		int cnt = result.getTotalPages();
		for(int i=0; i<cnt; i++) {
			paging = PageRequest.of(i, 3);
			frepo.findAll(paging).forEach(f->log.info(f.toString()));
			log.info("------------------------------");
		}
	}
	
	//@Test
	void deleteBoard() {
		brepo.deleteById(1L);
	}
	
	//@Test
	void getFileByBoard() {
		brepo.findById(1L).ifPresent(b->{
			for(PDSFile f:b.getFiles2()) {
				log.info(f.toString());
			}
		});
	}
	
	//@Test
	void fileDelete() {
		frepo.deleteById(5L);
	}
	
	//@Test
	void searchFile() {
		List<PDSBoard> blist = (List<PDSBoard>)brepo.findAll();
		for(PDSBoard board:blist) {
			List<PDSFile> flist = board.getFiles2();
			flist.forEach(f->{
				if(f.getFno() == 5L) {
					f.setPdsfilename("이상해씨.jpg");
					//brepo.save(board);
					frepo.save(f);
				}
			});
		}
	}
	
	//PPT 76p 
	//첨부파일 수정 //DML 수행
	@Transactional //이 클래스가 Test이기 때문에 DB에 반영되지는 않는다.(즉, Rollback이 된다. 결과를 반영하려면, class level에 @Commit을 추가해야 한다.)
	//@Test
	void updateFile2() {
		int result = brepo.updatePDSFile(2L, "이미지성형하기.jpg");
		log.info("결과: " + result);
	}
	
	//첨부파일 수정 //DML 수행
	//@Test
	void updateFile1() {
		PDSFile file = frepo.findById(1L).orElse(null);
		if(file == null) return;
		file.setPdsfilename("파일이름수정");
		frepo.save(file);
	}
	
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
