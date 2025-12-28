package com.carrental.CS393PROJECT.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.carrental.CS393PROJECT.dto.CarDTO;
import com.carrental.CS393PROJECT.model.Car;
import com.carrental.CS393PROJECT.model.CarCategory;
import com.carrental.CS393PROJECT.model.CarStatus;
import com.carrental.CS393PROJECT.model.Location;
import com.carrental.CS393PROJECT.model.Member;
import com.carrental.CS393PROJECT.model.Reservation;
import com.carrental.CS393PROJECT.model.ReservationStatus;
import com.carrental.CS393PROJECT.model.TransmissionType;
import com.carrental.CS393PROJECT.repos.CarRepository;
import com.carrental.CS393PROJECT.repos.LocationRepository;
import com.carrental.CS393PROJECT.repos.MemberRepository;
import com.carrental.CS393PROJECT.repos.ReservationRepository;

@SpringBootTest
@Transactional
public class CarServiceTest {

	@Autowired
	private CarService carService;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void testSearchAvailableCars_Success() {
		Location location = new Location(null, "Test Airport");
		location = locationRepository.save(location);

		Car car = new Car("B123456789", "34TST1234", 5, "Ford", "Focus", TransmissionType.MANUAL, 80.0,
				CarCategory.COMPACT_CAR, location);
		car.setStatus(CarStatus.AVAILABLE);
		carRepository.save(car);

		LocalDateTime start = LocalDateTime.now().plusDays(1);
		LocalDateTime end = LocalDateTime.now().plusDays(3);

		List<CarDTO> result = carService.searchAvailableCars(null, null, null, null, start, end, null,
				location.getCode());

		assertFalse(result.isEmpty());
		assertEquals("B123456789", result.get(0).getBarcode());
	}

	@Test
	public void testDeleteCar_Success() {
		Location location = new Location(null, "Test City");
		location = locationRepository.save(location);

		Car car = new Car("B987654321", "34DEL1234", 5, "Fiat", "Egea", TransmissionType.MANUAL, 50.0,
				CarCategory.COMPACT_CAR, location);
		carRepository.save(car);

		boolean isDeleted = carService.deleteCar("B987654321");

		assertTrue(isDeleted);
		assertFalse(carRepository.existsById("B987654321"));
	}

	@Test
	public void testDeleteCar_Fail_LinkedReservation() {
		Location location = new Location(null, "Test City");
		location = locationRepository.save(location);

		Car car = new Car("B78391467", "34LNK123", 5, "Honda", "Civic", TransmissionType.AUTOMATIC, 120.0,
				CarCategory.MIDSIZE_CAR, location);
		carRepository.save(car);

		Member member = new Member(null, "Fatma Yilmaz", "Fatih", "fatmayilmaz@test.com", "1234567", "DL999");
		memberRepository.save(member);

		Reservation reservation = new Reservation();
		reservation.setReservationNumber("RES12345");
		reservation.setCar(car);
		reservation.setMember(member);
		reservation.setPickUpLocation(location);
		reservation.setDropOffLocation(location);
		reservation.setPickUpDateTime(LocalDateTime.now());
		reservation.setDropOffDateTime(LocalDateTime.now().plusDays(2));
		reservation.setStatus(ReservationStatus.ACTIVE);
		reservationRepository.save(reservation);

		boolean isDeleted = carService.deleteCar("B78391467");

		assertFalse(isDeleted);
		assertTrue(carRepository.existsById("B78391467"));
	}
}