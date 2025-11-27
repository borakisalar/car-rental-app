package com.carrental.CS393PROJECT.repos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByDrivingLicenseNumber(String drivingLicenseNumber);
}
