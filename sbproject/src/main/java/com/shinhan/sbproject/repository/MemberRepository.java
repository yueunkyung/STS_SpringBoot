package com.shinhan.sbproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.MemberDTO;

public interface MemberRepository extends CrudRepository<MemberDTO, String> {
	
}
