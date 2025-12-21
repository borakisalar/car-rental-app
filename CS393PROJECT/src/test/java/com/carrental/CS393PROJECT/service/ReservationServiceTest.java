package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.dto.RentedCarDTO;
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
import com.carrental.CS393PROJECT.repos.ReservationRepository;

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

	@Autowired
	private ReservationRepository reservationRepository;

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

		Car car = new Car("CAR133", "34AB123", 5, "Toyota", "Corolla", TransmissionType.AUTOMATIC, 100.0,
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
	void returnCar_Success() {
		Location pickupLoc = locationRepository.save(new Location(null, "Istanbul"));
		Location dropoffLoc = locationRepository.save(new Location(null, "Izmir"));

		Car car = new Car("CAR123", "34CAR1234", 5, "BMW", "X5", TransmissionType.AUTOMATIC, 1200.0,
				CarCategory.LUXURY_CAR, pickupLoc);
		carRepository.save(car);

		Reservation res = new Reservation();
		res.setReservationNumber("83940174");
		res.setCar(car);
		res.setPickUpLocation(pickupLoc);
		res.setDropOffLocation(dropoffLoc);
		res.setStatus(ReservationStatus.ACTIVE);
		reservationRepository.save(res);

		boolean result = reservationService.returnCar("83940174");

		assertTrue(result);

		Reservation updatedRes = reservationRepository.findByReservationNumber("83940174").get();
		assertEquals(ReservationStatus.COMPLETED, updatedRes.getStatus());
		assertNotNull(updatedRes.getReturnDate());

		Car updatedCar = carRepository.findById("CAR123").get();
		assertEquals(dropoffLoc.getCode(), updatedCar.getLocation().getCode());
	}

	@Test
	void deleteReservation_Success() {
		Location loc = locationRepository.save(new Location(null, "Loc"));
		Member member = memberRepository
				.save(new Member(null, "Asli", "Cekmekoy", "fatma@test.com", "5555555", "DL1234"));
		Car car = carRepository.save(new Car("CAR321", "34CAR4321", 5, "Seat", "Leon", TransmissionType.MANUAL, 1150.0,
				CarCategory.COMPACT_CAR, loc));

		Reservation res = new Reservation();
		res.setReservationNumber("87934294");
		res.setCar(car);
		res.setMember(member);
		reservationRepository.save(res);

		boolean result = reservationService.deleteReservation("87934294");

		assertTrue(result);
		assertFalse(reservationRepository.findByReservationNumber("87934294").isPresent());

		assertTrue(memberRepository.findById(member.getId()).isPresent());
		assertTrue(carRepository.findById(car.getBarcode()).isPresent());
	}

	@Test
	void getAllRentedCars_ReturnsActiveOnly() {
		Location loc = locationRepository.save(new Location(null, "Esenyurt"));
		Car car = new Car("CAR485", "34RNT9999", 5, "Tesla", "Model Y", TransmissionType.AUTOMATIC, 150.0,
				CarCategory.LUXURY_CAR, loc);
		carRepository.save(car);

		Member member = memberRepository.save(new Member(null, "Mehmet", "Home", "mehmet@test.com", "555", "DL1234"));

		Reservation activeRes = new Reservation();
		activeRes.setReservationNumber("74839453");
		activeRes.setCar(car);
		activeRes.setMember(member);
		activeRes.setPickUpLocation(loc);
		activeRes.setDropOffLocation(loc);
		activeRes.setPickUpDateTime(LocalDateTime.now().minusDays(1));
		activeRes.setDropOffDateTime(LocalDateTime.now().plusDays(1));
		activeRes.setStatus(ReservationStatus.ACTIVE);
		reservationRepository.save(activeRes);

		Reservation completedRes = new Reservation();
		completedRes.setReservationNumber("639284856");
		completedRes.setCar(car);
		completedRes.setMember(member);
		completedRes.setPickUpLocation(loc);
		completedRes.setDropOffLocation(loc);
		completedRes.setPickUpDateTime(LocalDateTime.now().minusDays(10));
		completedRes.setDropOffDateTime(LocalDateTime.now().minusDays(5));
		completedRes.setStatus(ReservationStatus.COMPLETED);
		reservationRepository.save(completedRes);

		List<RentedCarDTO> results = reservationService.getAllRentedCars();

		assertFalse(results.isEmpty(), "Should return at least the active car we just added");

		boolean containsActive = results.stream().anyMatch(dto -> dto.getReservationNumber().equals("74839453"));
		assertTrue(containsActive, "The list should contain the active reservation '74839453'");

		boolean containsOld = results.stream().anyMatch(dto -> dto.getReservationNumber().equals("639284856"));
		assertFalse(containsOld, "The list should NOT contain the completed reservation '639284856'");
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