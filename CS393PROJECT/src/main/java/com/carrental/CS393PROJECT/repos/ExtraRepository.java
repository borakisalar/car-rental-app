package com.carrental.CS393PROJECT.repos;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.ExtraService;

@Repository
public interface ExtraRepository {
	Optional<ExtraService> findByName(String name);
}
