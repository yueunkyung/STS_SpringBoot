package com.shinhan.sbproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.sbproject.repository.MemberRepository;
import com.shinhan.sbproject.repository.ProfileRepository;
import com.shinhan.sbproject.vo.MemberDTO;
import com.shinhan.sbproject.vo.MemberRole;
import com.shinhan.sbproject.vo.ProfileDTO;
import com.shinhan.sbproject.vo.QProfileDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTest {
	@Autowired
	MemberRepository mrepo;
	@Autowired
	ProfileRepository prepo;
	
	//@Test
	void ff() {
		assertEquals(1+2, 3);
	}
	
	//1.currentYn == true인 profile 조회하기
	//2.member와 profile 조인 문장 (nativeQuery) 작성
	//3.동적SQL(fname like %)
	//@Test
	void 동적(){
		BooleanBuilder builder = new BooleanBuilder();
		QProfileDTO profile = QProfileDTO.profileDTO;
		builder.and(profile.pno.gt(7L));
		builder.and(profile.fname.like("%파일%"));
		builder.and(profile.currentYn.eq(false));
		
		prepo.findAll(builder).forEach(pro->{
			log.info(pro.toString());
		});
	}
	
	//@Test
	void 조인(){
		prepo.selectByJoin().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}
	
	//@Test
	void 승광(){
		prepo.findByCurrentYnIsTrue().forEach(pro->{
            log.info("Fname:" + pro.getFname());
            log.info("Mname:" + pro.getMember().getMname());
            assert pro.isCurrentYn();
            log.info("--------------------------------------");
        });
	}
	
	//단방향 처리 : Profile을 통해서 Member 가져오기 (Profile이 Member을 참조하므로)
	//(memberId를 알고 Profile 가져오기 가능?)ManyToOne 불가
	//pno를 가지고 Member를 가져오기 가능? Yes
	//@Test
	void memberProfile() {
		Long pno = 10L;
		prepo.findById(pno).ifPresent(pro->{
			log.info("Fname: "+ pro.getFname());
			log.info("Mname: "+ pro.getMember().getMname() );
		});
		System.out.println("------------------------------------------");
		MemberDTO member = new MemberDTO();
		member.setMid("admin10");
		prepo.findByMember(member).forEach(pro->{
			log.info("Fname: "+ pro.getFname());
			log.info("Mname: "+ pro.getMember().getMname() );
		});
	}
	
	//@Test
	void profileSelect() {
		prepo.findAll().forEach(pro->{
			log.info(pro.toString());
			log.info("건수 : " + prepo.count());
		});
	}
	
	//@Test
	void profileInsert() {
		//MemberDTO MemberDTO = mrepo.findById("user1").orElse(null);
		//MemberDTO MemberDTO = mrepo.findById("manager7").orElse(null);
		MemberDTO MemberDTO = mrepo.findById("admin10").orElse(null);
		
		IntStream.rangeClosed(1, 5).forEach(i->{
			ProfileDTO profile = ProfileDTO.builder()
					.fname("어드민프로파일"+i)
					.currentYn(i<5?false:true)
					.member(MemberDTO)
					.build();
			prepo.save(profile);
		});
	}
	
	//@Test
	void memberSelect() {
		mrepo.findAll().forEach(m->{
			log.info(m.toString());
		});
	}
	
	//@Test
	void memberInsert() {
		//10명의 member 입력하기 1~5: user 권한, 6~8:manager권한, 9~10: admin권한
		MemberDTO member = new MemberDTO();
		UUID uuid = UUID.randomUUID();
		member.setMpassword(uuid.toString().split("-")[0]);
		
		IntStream.rangeClosed(1, 10).forEach(i->{
			if(i<=5) {
				member.setMid("user" + i);
				member.setMname("유저" + i);
				member.setMrole(MemberRole.USER);
			} else if(i>=6 && i<=8) {
				member.setMid("manager" + i);
				member.setMname("매니져" + i);
				member.setMrole(MemberRole.MANAGER);
			} else {
				member.setMid("admin" + i);
				member.setMname("어드민" + i);
				member.setMrole(MemberRole.ADMIN);
			}
			
			mrepo.save(member);
			
		});
	}
}
