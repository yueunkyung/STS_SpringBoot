package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.DeptVO;

// 1.기본 CRUD ==> CrudRepository 상속 : findAll, findById, save, count, delete
public interface DeptRepository extends CrudRepository<DeptVO, Integer>{
	
	// 2.규칙에 맞는 함수 정의
	// 특정 managerId가 관리하는 부서들의 부서이름 뒤에 "OK"라는 문자를 추가(수정)
	
	List<DeptVO> findBymanagerId(int mid);

}
