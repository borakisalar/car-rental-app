package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.dto.ReservationRequestDTO;
import com.carrental.CS393PROJECT.dto.ReservationResponseDTO;
import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.ExtraService;
import com.carrental.CS393PROJECT.model.Location;
import com.carrental.CS393PROJECT.model.Member;
import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.model.ReservationStatus;
import com.carrental.CS393PROJECT.model.TransmissionType;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.ExtraServiceRepository;
import com.carrental.CS393PROJECT.repos.LocationRepository;
import com.carrental.CS393PROJECT.repos.MemberRepository;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ExtraServiceRepository extraServiceRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private MemberRepository memberRepository;

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

	@Test
	public void testMakeReservation_Success() {
		Location pickupLoc = new Location(null, "Istanbul Airport");
		Location dropoffLoc = new Location(null, "City Center");
		pickupLoc = locationRepository.save(pickupLoc);
		dropoffLoc = locationRepository.save(dropoffLoc);

		Member member = new Member(null, "Arda Ayas", "Mardin", "ardaayas@example.com", "555-1234", "DL12345");
		member = memberRepository.save(member);

		Car car = new Car("CAR123", "34AB123", 5, "Toyota", "Corolla", TransmissionType.AUTOMATIC, 100.0,
				CarCategory.MIDSIZE_CAR, pickupLoc);
		car.setStatus(CarStatus.AVAILABLE);
		carRepository.save(car);

		ReservationRequestDTO request = new ReservationRequestDTO();
		request.setCarBarcode("CAR123");
		request.setMemberId(member.getId());
		request.setPickUpLocationCode(pickupLoc.getCode());
		request.setDropOffLocationCode(dropoffLoc.getCode());
		request.setPickUpDateTime(LocalDateTime.now().plusDays(1));
		request.setDropOffDateTime(LocalDateTime.now().plusDays(3));
		request.setExtraIds(null);

		ReservationResponseDTO response = reservationService.makeReservation(request);

		assertNotNull(response);
		assertNotNull(response.getReservationNumber());
		assertTrue(response.getReservationNumber().length() == 8);
		assertEquals(member.getId(), response.getMemberId());
		assertEquals(pickupLoc.getName(), response.getPickUpLocationName());
		assertEquals(200.0, response.getTotalAmount(), 0.01);
	}

	@Test
	void addExtraToReservation_Success() {
		Reservation res = new Reservation();
		res.setReservationNumber("EXTRASERVICE");
		res.setStatus(ReservationStatus.ACTIVE);
		reservationService.saveReservation(res);

		ExtraService extra = new ExtraService();
		extra.setName("Cooling Seat");
		extra.setPrice(100.0);
		extraServiceRepository.save(extra);

		boolean result = reservationService.addExtraToReservation("EXTRASERVICE", extra.getId());

		assertTrue(result);
	}
}