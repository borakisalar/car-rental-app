package com.carrental.CS393PROJECT.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.CS393PROJECT.model.Member;
import com.carrental.CS393PROJECT.repos.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	};
	
	public Member registerMember(Member member) {
		return memberRepository.save(member);
	}
	
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

}
