package com.carrental.CS393PROJECT.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarStatus;

@Repository
public interface CarRepository extends JpaRepository<Car, String>{

	List<Car> findByStatus(CarStatus status);
	
	List<Car> findByLocation(CarStatus status);
	
	@Query(value = "SELECT * FROM cars WHERE status = 'AVAILABLE' AND transmission_type = :transmission", nativeQuery = true)
    List<Car> findAvailableByTransmission(@Param("transmission") String transmissionType);
}
