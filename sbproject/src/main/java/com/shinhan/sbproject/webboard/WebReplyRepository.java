package com.shinhan.sbproject.webboard;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WebReplyRepository extends CrudRepository<WebReply, Long>
						, PagingAndSortingRepository<WebReply, Long>
						, QuerydslPredicateExecutor<WebReply> {

	//기본 CRUD제공
	//추가 메서드(규칙에 맞게 정의)
	List<WebReply> findByBoard(WebBoard board);
}
