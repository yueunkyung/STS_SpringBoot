package com.shinhan.sbproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.DeptVO;

//CrudRepository 상속 : findAll, findById, save, count, delete
public interface DeptRepository extends CrudRepository<DeptVO, Integer>{

}
