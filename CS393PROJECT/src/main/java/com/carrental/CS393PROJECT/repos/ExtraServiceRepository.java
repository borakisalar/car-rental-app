package com.carrental.CS393PROJECT.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.ExtraService;


@Repository
public interface ExtraServiceRepository extends JpaRepository<ExtraService, Long>{
	Optional<ExtraService> findById(Long name);
}
