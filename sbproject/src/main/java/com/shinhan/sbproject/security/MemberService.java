package com.shinhan.sbproject.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;	//spring security
import org.springframework.security.core.userdetails.UserDetailsService;	//spring security
import org.springframework.security.core.userdetails.UsernameNotFoundException;	//spring security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;	//spring security
import org.springframework.stereotype.Service;

import com.shinhan.sbproject.repository.MemberRepository;
import com.shinhan.sbproject.vo.MemberDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class MemberService implements UserDetailsService {
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private MemberRepository mrepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder; // security config에서 Bean생성
	// 회원가입
	
	//@Transactional
	public MemberDTO joinUser(MemberDTO member) {
		// 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		String pwd = passwordEncoder.encode(member.getMpassword());
		member.setMpassword(pwd);
		System.out.println("암호화된 pass:" + pwd);
		return mrepo.save(member);
	}

	//!!!!반드시 구현해야한다. 
	@Override
	//@Transactional	//name="username" 파라메터로 전달된다.
	public UserDetails loadUserByUsername(String aa) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername mid:" + aa);
		//filter는 조건에 맞는것만 선택
		//map: 변형한다. 

		Optional<MemberDTO> optionMember = mrepo.findById(aa);
		MemberDTO loginUser = optionMember.orElse(null);

		UserDetails member = mrepo.findById(aa).filter(m -> m != null).map(m -> new SecurityUser(m)).get();
		System.out.println("UserDetails member:" + member);
		httpSession.setAttribute("user", loginUser);
		return member;
	}

}
