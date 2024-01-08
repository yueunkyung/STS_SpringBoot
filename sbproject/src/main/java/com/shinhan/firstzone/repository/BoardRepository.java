package com.shinhan.firstzone.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.BoardVO;

//1.기본 CRUD작업 => CrudRepository 상속 : findAll, findById, save, count, delete
public interface BoardRepository extends CrudRepository<BoardVO, Long> {
	//2. 규칙에 맞는 메서드 정의 	//함수를 정의하는것, 구현은 아님
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

}
