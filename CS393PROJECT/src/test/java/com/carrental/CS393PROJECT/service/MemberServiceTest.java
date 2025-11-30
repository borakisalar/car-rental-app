package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.model.Member;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Test
	void registerMember_Success() {
		Member member = new Member();
		member.setName("Ayse");
		member.setEmail("ayse@test.com");

		Member saved = memberService.registerMember(member);

		assertNotNull(saved.getId());
		assertEquals("Ayse", saved.getName());
	}

	@Test
	void getAllMembers_ReturnsList() {
		Member member = new Member();
		member.setName("Ahmet");
		memberService.registerMember(member);

		List<Member> members = memberService.getAllMembers();
		assertFalse(members.isEmpty());
	}
}