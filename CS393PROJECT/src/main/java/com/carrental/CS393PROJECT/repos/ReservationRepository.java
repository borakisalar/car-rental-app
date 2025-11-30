package com.carrental.CS393PROJECT.repos;
import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String>{

	@Query("SELECT r FROM Reservation r WHERE r.car.barcode = :carBarcode " +
	           "AND r.status = com.carrental.CS393PROJECT.model.ReservationStatus.ACTIVE " +
	           "AND (r.pickUpDateTime <= :dropoffDate AND r.dropOffDateTime >= :pickupDate)")
	    List<Reservation> findOverlappingReservations(
	            @Param("carBarcode") String carBarcode,
	            @Param("pickupDate") LocalDateTime pickupDate,   
	            @Param("dropoffDate") LocalDateTime dropoffDate
	    );
}
