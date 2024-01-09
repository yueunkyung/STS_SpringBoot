package com.shinhan.firstzone.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sbproject.vo.BoardVO;

//1.기본 CRUD작업 => CrudRepository 상속 : findAll, findById, save, count, delete
//CrudRepository상속받은 PagingAndSortingRepository
public interface BoardRepository extends CrudRepository<BoardVO, Long>
										, PagingAndSortingRepository<BoardVO, Long>
										, QuerydslPredicateExecutor<BoardVO> {
	//2. 규칙에 맞는 메서드 정의 	//함수를 정의하는것, 구현은 아님
	//https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	List<BoardVO> findByWriter(String writer2); //where writer = ?
	List<BoardVO> findByContent(String content2); //where content = ?
	List<BoardVO> findByBnoGreaterThan(Long bno); //where bno > ?
	List<BoardVO> findByContentLike(String content2); //where content like ?
	List<BoardVO> findByContentContaining(String content2); //where content like '%'||?||'%' ?
	List<BoardVO> findByBnoGreaterThanAndBnoLessThanEqual(Long bno, Long bno2); //where bno>? and bno<=?
	
	List<BoardVO> findByBnoLessThan(Long bno); //where bno<?
	List<BoardVO> findByWriterOrderByRegDateDesc(String writer); //where writer=? order by RegDateDesc
	List<BoardVO> findByContentContainingOrTitleContaining(String Content2, String title); // where content like '%'||?||'%' or Title like '%'||?||'%'
	List<BoardVO> findByRegDateBetweenAndBnoBetweenAndWriterNotLikeAndTitleContainingAndContentNotNullOrderByBno(Timestamp date,Timestamp date2, long bno1, long bno2,String writer, String title);
    //where RegDate between ? and ?
	//and bno between ? and ?
	//and Writer not like ?
	//and title like '%||?||'%'
	//and content is not null
	//order by bno

	//특정 wirter가 작성한 board 건 수
	int countByWriter(String writer2);
	
	
	//Paging, Sort 추가...Oracle 12c release부터 사용할 수 있는 구문이다. (OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY;)
	List<BoardVO> findByBnoGreaterThan(Long bno, Pageable paging);
	Page<BoardVO> findByBnoBetween(Long bno1,Long bno2,Pageable paging);
	
	
	//3.JPQL(JPA Query Language) : 규칙에 맞는 함수정의가 한계가 있다. 이를 해결하는 방법이다.
	@Query("select b from BoardVO b "
			+ "where b.title like %?2% and b.writer like %?3% and b.bno > ?1 "
			+ "order by bno desc") //BoardVO ==> Entity 이름임.
	List<BoardVO> selectByTitleAndWriter2(Long bno, String title, String writer);
	
	//SQL문(nativeQuery), 테비블 이름, * 가능
	@Query(value = "select * from tbl_boards_ek b "
			+ "where b.title like %?2% and b.writer like %?3% and b.bno > ?1 "
			+ "order by bno desc", nativeQuery = true) //BoardVO ==> Entity 이름임.
	List<BoardVO> selectByTitleAndWriter3(Long bno, String title, String writer);

	@Query("select b from BoardVO b "
			+ "where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb "
			+ "order by bno desc") //BoardVO ==> Entity 이름임.
	List<BoardVO> selectByTitleAndWriter4(@Param("bb") Long bno, @Param("tt") String title, @Param("ww") String writer);

	@Query("select b from #{#entityName} b "
			+ "where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb "
			+ "order by bno desc") //BoardVO ==> Entity 이름임.
	List<BoardVO> selectByTitleAndWriter5(@Param("bb") Long bno, @Param("tt") String title, @Param("ww") String writer);
	
	@Query("select b.title, b.writer, b.content from #{#entityName} b "
			+ "where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb "
			+ "order by bno desc") //BoardVO ==> Entity 이름임.
	List<Object[]> selectByTitleAndWriter6(@Param("bb") Long bno, @Param("tt") String title, @Param("ww") String writer);
	
	@Query("select board.title, board.content, board.writer, board.bno, board.regDate from #{#entityName} board where board.writer = :wr")
	List<String[]> selectByWriter(@Param("wr") String writer);
	
}
