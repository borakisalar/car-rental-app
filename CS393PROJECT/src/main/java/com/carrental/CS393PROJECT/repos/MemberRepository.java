package com.carrental.CS393PROJECT.repos;

import java.lang.reflect.Member;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

	Optional<Member> findById(Long id);
	
	
}
