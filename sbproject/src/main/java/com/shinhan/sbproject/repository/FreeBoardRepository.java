package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sbproject.vo3.FreeBoard;


public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long>
										, PagingAndSortingRepository<FreeBoard, Long>
										, QuerydslPredicateExecutor<FreeBoard>{

	//이름 규칙에 맞는 함수 추가함
	List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	List<FreeBoard> findByBnoBetween(Long bno1, Long bno2, Pageable page);
	Page<FreeBoard> findByWriter(String writer, Pageable page);
	
	
	//Title이 특정 문자를 포함하는 board를 얻기, sort, 특정 칼럼만 select
	@Query("select free.bno, free.title, free.writer from FreeBoard free where title like %?1% order by free.bno desc")
	List<Object[]> selectBytitle(String title);
	 
	@Query("select free.bno, free.title, free.writer from #{#entityName} free where title like %:tt% order by free.bno desc")
	List<Object[]> selectBytitle2(@Param("tt") String title);

	@Query(value="select free.bno, free.title, free.writer from tbl_freeboards free"
			+ " where title like concat('%',:tt,'%') order by free.bno desc", nativeQuery = true)
	List<Object[]> selectBytitle3(@Param("tt") String title);
	 
}
