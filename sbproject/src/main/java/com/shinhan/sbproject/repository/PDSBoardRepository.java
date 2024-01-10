package com.shinhan.sbproject.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo2.PDSBoard;

import jakarta.transaction.Transactional;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {

	//PPT 76p
	//@Query : select만 가능
	//DML수행은 @@Modifying 추가한다.(Test시 @Transactional 포함)
	@Modifying
	@Query("UPDATE PDSFile f set f.pdsfilename = ?2 WHERE f.fno = ?1") 
	int updatePDSFile(Long fno, String newFileName); 
	
}
