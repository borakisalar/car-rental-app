package com.carrental.CS393PROJECT.repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.Location;
import com.carrental.CS393PROJECT.model.TransmissionType;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

	List<Car> findByStatus(CarStatus status);

	List<Car> findByLocation(Location location);

	@Query("SELECT c FROM Car c WHERE c.status = 'AVAILABLE' AND c.transmissionType = :transmission")
	List<Car> findAvailableByTransmission(@Param("transmission") TransmissionType transmissionType);

	@Query("SELECT c FROM Car c WHERE " + "c.status = 'AVAILABLE' "
			+ "AND (:location IS NULL OR c.location = :location) "
			+ "AND (:category IS NULL OR c.category = :category) "
			+ "AND (:transmission IS NULL OR c.transmission = :transmission) "
			+ "AND (:minPrice IS NULL OR c.dailyPrice >= :minPrice) "
			+ "AND (:maxPrice IS NULL OR c.dailyPrice <= :maxPrice) "
			+ "AND (:seatCount IS NULL OR c.seatCount = :seatCount) " + "AND c.barcode NOT IN ( "
			+ "    SELECT r.car.barcode FROM Reservation r " + "    WHERE r.status = 'ACTIVE' " + "    AND ( "
			+ "       (r.pickupDate < :dropoffDate AND r.dropoffDate > :pickupDate) " + "    ) " + ")")
	List<Car> searchAvailableCars(@Param("location") String location, @Param("category") CarCategory category,
			@Param("transmission") TransmissionType transmission, @Param("minPrice") Double minPrice,
			@Param("maxPrice") Double maxPrice, @Param("seatCount") Integer seatCount,
			@Param("pickupDate") LocalDateTime pickupDate, @Param("dropoffDate") LocalDateTime dropoffDate);
}
