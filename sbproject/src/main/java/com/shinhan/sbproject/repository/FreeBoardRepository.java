package com.shinhan.sbproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.sbproject.vo3.FreeBoard;
import java.util.List;


public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long>
										, PagingAndSortingRepository<FreeBoard, Long>
										, QuerydslPredicateExecutor<FreeBoard>{

	//이름 규칙에 맞는 함수 추가함
	List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	List<FreeBoard> findByBnoBetween(Long bno1, Long bno2, Pageable page);
	Page<FreeBoard> findByWriter(String writer, Pageable page);
}
