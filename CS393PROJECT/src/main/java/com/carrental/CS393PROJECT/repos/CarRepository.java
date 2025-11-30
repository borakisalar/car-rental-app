package com.carrental.CS393PROJECT.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.Location;
import com.carrental.CS393PROJECT.model.TransmissionType;

@Repository
public interface CarRepository extends JpaRepository<Car, String>{

	List<Car> findByStatus(CarStatus status);
	
	List<Car> findByLocation(Location location);
	
	@Query("SELECT c FROM Car c WHERE c.status = 'AVAILABLE' AND c.transmissionType = :transmission")
    List<Car> findAvailableByTransmission(@Param("transmission") TransmissionType transmissionType);
}
