package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo2.PDSFile;

public interface PDSFileRepository extends CrudRepository<PDSFile, Long>{
	 List<PDSFile> findByPdsfilenameContaining(String fname);
	 
	 @Query(value="select board.pname, count(file.fno)"
	 		+ " from tbl_pdsboard board left outer join tbl_pdsfiles file"
	 		+ " on (file.pdsno = board.pid) group by board.pname", nativeQuery = true)
	 List<Object[]> getFileCountByBoard();
	 
	 
	 //PPT 74p
	 @Query("select b.pname, count(f ) "
			 + " from PDSBoard b left outer join b.files2 f where b.pid>0 "
			 + " group by b.pname order by b.pname ")
	 public List<Object[]> getFilesCount();
}
