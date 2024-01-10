package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.MemberDTO;

public interface MemberRepository extends CrudRepository<MemberDTO, String> {
	
	//멤버정보를 이용해서 Profile의 개수를 알아내기
	//JPQL 이용
	@Query("select count(p) from MemberDTO m inner join ProfileDTO p on(m=p.member) "
			+ "where m.mid=?1")
	int getProfileCountByMember(String mid);
	
	//Boot2: on(m.mid = p.member)
	//Boot3: on(m = p.member)
	@Query("select m.mid, count(p) "
			+ "from MemberDTO m "
			+ "left outer join ProfileDTO p on(m=p.member) "
			+ "group by m.mid")
	List<Object[]> getProfileCountByMember();
	
	//nativeQuery
	@Query(value="select m.mid, count(p.member_mid) "
			+ "from tbl_members_ek m "
			+ "left outer join tbl_profile_ek p "
			+ "on(m.mid = p.member_mid) "
			+ "group by m.mid", nativeQuery = true)
	List<Object[]> getProfileCountByMember2();
}
