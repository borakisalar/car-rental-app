package com.carrental.CS393PROJECT.repos;

import java.util.Optional;

import com.carrental.CS393PROJECT.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
	Optional<Location> findByCode(String code);
}
