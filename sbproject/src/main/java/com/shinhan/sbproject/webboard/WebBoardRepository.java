package com.shinhan.sbproject.webboard;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>
						, PagingAndSortingRepository<WebBoard, Long>
						, QuerydslPredicateExecutor<WebBoard> {
	
	//default function 추가
	//column : title, content, writer
	//keyword : %aa%
	public default Predicate makePredicate(String column, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoard board = QWebBoard.webBoard;
		
		if(column == null) return builder;
		if(column.equals("전체")) {
			builder.or(board.title.like("%"+keyword+"%"));
			builder.or(board.content.like("%"+keyword+"%")); 
			builder.or(board.writer.like("%"+keyword+"%"));
			//System.out.println(builder);
			return builder;
		}
		
		switch(column) {
		case "title":
			builder.and(board.title.like("%"+keyword+"%")); break;
		case "content":
			builder.and(board.content.like("%"+keyword+"%")); break;
		case "writer":
			builder.and(board.writer.like("%"+keyword+"%")); break;
		}
		
		return builder;
	}

}
