package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.ExtraService;
import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.model.ReservationStatus;
import com.carrental.CS393PROJECT.model.TransmissionType;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ExtraServiceRepository;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ExtraServiceRepository extraServiceRepository;

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

	@Test
	void cancelReservation_Success() {
		Reservation res = new Reservation();
		res.setReservationNumber("55555555");
		res.setStatus(ReservationStatus.ACTIVE);
		reservationService.saveReservation(res);

		boolean result = reservationService.cancelReservation("55555555");

		assertTrue(result);
	}

	void makeReservation_Success() {
		Car car = new Car();
		car.setBarcode("EXAMPLEBARCODE");
		car.setStatus(CarStatus.AVAILABLE);
		car.setBrand("TestBrand");
		car.setModel("TestModel");
		car.setLicensePlateNumber("34EXP1234");
		car.setNumberOfSeats(5);
		car.setTransmissionType(TransmissionType.AUTOMATIC);
		carRepository.save(car);

		Reservation res = reservationService.makeReservation("EXAMPLEBARCODE", LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(3));

		assertNotNull(res);
		assertNotNull(res.getReservationNumber());
		assertEquals("EXAMPLEBARCODE", res.getCar().getBarcode());
		assertEquals(ReservationStatus.ACTIVE, res.getStatus());
	}

	@Test
	void addExtraToReservation_Success() {
		Reservation res = new Reservation();
		res.setReservationNumber("EXTRASERVICE");
		res.setStatus(ReservationStatus.ACTIVE);
		reservationService.saveReservation(res);

		ExtraService extra = new ExtraService();
		extra.setName("GPS");
		extra.setPrice(100.0);
		extraServiceRepository.save(extra);

		boolean result = reservationService.addExtraToReservation("EXTRASERVICE", extra.getId());

		assertTrue(result);
	}
}