package com.carrental.CS393PROJECT.repos;

import com.carrental.CS393PROJECT.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

	@Query("SELECT r FROM Reservation r WHERE r.car.barcode = :carBarcode "
			+ "AND r.status = com.carrental.CS393PROJECT.model.ReservationStatus.ACTIVE "
			+ "AND (r.pickUpDateTime <= :dropoffDate AND r.dropOffDateTime >= :pickupDate)")
	List<Reservation> findOverlappingReservations(@Param("carBarcode") String carBarcode,
			@Param("pickupDate") LocalDateTime pickupDate, @Param("dropoffDate") LocalDateTime dropoffDate);

	@Query("SELECT r FROM Reservation r " + "WHERE r.status = 'ACTIVE' "
			+ "AND :now BETWEEN r.pickupDate AND r.dropoffDate")
	List<Reservation> findCurrentlyRentedCars(@Param("now") LocalDateTime now);

	@Query("SELECT COUNT(r) > 0 FROM Reservation r " + "WHERE r.car.barcode = :barcode " + "AND r.status = 'ACTIVE' "
			+ "AND ((:start BETWEEN r.pickupDate AND r.dropoffDate) "
			+ "OR (:end BETWEEN r.pickupDate AND r.dropoffDate) " + "OR (r.pickupDate BETWEEN :start AND :end))")
	boolean isCarBookedInPeriod(@Param("barcode") String barcode, @Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);
	
	Optional<Reservation> findByReservationNumber(String reservationNumber);
	
	boolean existsByCarBarcode(String barcode);
}
