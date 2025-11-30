package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.model.Reservation;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Test
	void saveReservation_Success() {
		Reservation res = new Reservation();
		res.setReservationNumber("12345678");
		res.setPickUpDateTime(LocalDateTime.now().plusDays(3));
		res.setDropOffDateTime(LocalDateTime.now().plusDays(5));

		Reservation saved = reservationService.saveReservation(res);

		assertNotNull(saved);
		assertNotNull(saved.getReservationNumber());
	}

	@Test
	void getAllReservations_ReturnsList() {
		Reservation res = new Reservation();
		res.setReservationNumber("87654321");
		reservationService.saveReservation(res);

		List<Reservation> list = reservationService.getAllReservations();
		assertFalse(list.isEmpty());
	}
}